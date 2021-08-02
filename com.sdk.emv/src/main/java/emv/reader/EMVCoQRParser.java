package emv.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class EMVCoQRParser {

	protected static final int LENGTH = 2;

	protected static final String DEFAULT_COUNTRY_CODE = "KH";

	protected static final Logger LOGGER = LoggerFactory.getLogger(EMVCoQRParser.class);

	private String qrCodeContent;

	private List<EmvData> emvDataList;

	public EMVCoQRParser(String qrCodeContent) throws Exception {
		this.qrCodeContent = qrCodeContent;
		this.emvDataList = this.parseEmvCoQR(qrCodeContent);
	}

	public String getQrCodeContent() {
		return qrCodeContent;
	}

	public List<EmvData> getEmvDataList() {
		return emvDataList;
	}

	public Optional<String> getTagValue(int tag) {
		try {
			return emvDataList
					.stream()
					.filter(emv -> emv.getTagId() == tag)
					.map(EmvData::getValue)
					.findFirst();
		} catch (Exception e) {
			LOGGER.error("", e);
			return Optional.empty();
		}
	}

	public Optional<String> getSubTagValueByTag(int tag, int subTag) {
		try {
			Optional<EmvData> mainTag = emvDataList
					.stream()
					.filter(emv -> emv.getTagId() == tag)
					.findFirst();
			if(mainTag.isPresent()) {
				return mainTag.get()
						.getSubEmvs()
						.stream()
						.filter(emvData -> emvData.getTagId() == subTag)
						.map(EmvData::getValue)
						.findFirst();
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return Optional.empty();
	}

	protected Optional<Integer> getSafeTagIndex(String data, int index, int tagIndex) {
		try {
			return Optional.of(Integer.parseInt(data.substring(index, tagIndex)));
		} catch (Exception e) {
			//LOGGER.error("", e);
			return Optional.empty();
		}
	}

	protected Optional<String> getSafeTagValue(String data, int index, int tagIndex) {
		try {
			return Optional.of(data.substring(index, tagIndex));
		} catch (Exception e) {
			//LOGGER.error("", e);
			return Optional.empty();
		}
	}

	//strict checking
	public boolean isValid(String expectedMCC) {
		return this.isValid(DEFAULT_COUNTRY_CODE, Arrays.asList(expectedMCC), false);
	}

	//strict checking
	public boolean isValid(List<String> listExpectedMCC) {
		return this.isValid(DEFAULT_COUNTRY_CODE, listExpectedMCC, false);
	}

	//loose checking
	public boolean isValid() {
		return this.isValid(DEFAULT_COUNTRY_CODE, null, true);
	}

	public boolean isValid(String expectedCC, List<String> listExpectedMCC, boolean isIgnoreCRCCaseSensitivity) {
		if (this.qrCodeContent.length() < 20) {
			return false;
		}
		//get value of Merchant Category Code
		String thisMCC = this.getTagValue(52).orElse("");
		//get value of Country Code
		String thisCC = this.getTagValue(58).orElse("");
		//get value of CRC16 (last 4 digits)
		String thisCRC16 = isIgnoreCRCCaseSensitivity
				? this.getTagValue(63).orElse("").toUpperCase()
				: this.getTagValue(63).orElse("");
		//regenerate CRC16 (without last 4 digits)
		String regenerateCRC16 = CRC16Util.generateEMV(this.qrCodeContent);
		//compare
		boolean isValid = thisCRC16.equals(regenerateCRC16)
				&& !emvDataList.isEmpty()
				&& thisCC.equals(expectedCC);
		if (listExpectedMCC != null) {
			isValid = isValid && listExpectedMCC.contains(thisMCC);
		}
		if (!isValid) {
			LOGGER.info("(thisCRC16 [{}] equals regenerateCRC16 [{}]) (emvDataList.size() [{}]) (thisCC [{}] equals expectedCC [{}]) (listExpectedMCC {} contains thisMCC [{}])",
					thisCRC16, regenerateCRC16, emvDataList.size(), thisCC, expectedCC, listExpectedMCC, thisMCC);
		}
		return isValid;
	}

	protected List<EmvData> parseEmvCoQR(String str) throws Exception {
		if (!CRC16Util.validateChecksumCRC16(str)){
			throw new Exception("EMV QR is invalid CRC check sum");
		}
		try {
			int i = 0;
			int j;
			List<EmvData> emvDataList = new ArrayList<>();
			/**
			 * Read EMV QR Tag, Length and value
			 */
			while (i < str.length()) {
				int tagInd = i + LENGTH;
				Optional<Integer> tag = this.getSafeTagIndex(str, i, tagInd);
				Optional<Integer> lengthValue = this.getSafeTagIndex(str, tagInd, (tagInd + LENGTH));
				EmvData emvData = new EmvData();
				emvData.setLength(lengthValue.get());
				emvData.setValue(this.getSafeTagValue(str, (tagInd + LENGTH), (tagInd + LENGTH + lengthValue.get())).get());
				emvData.setTagId(tag.get());
				emvData.setSubEmvs(new ArrayList<>());
				j = 0;
				/**
				 * Read sub EMV QR Tag, Length and value
				 */
				while (j < emvData.getValue().length()) {
					int subTagInd = j + LENGTH;
					Optional<Integer> subTag = this.getSafeTagIndex(emvData.getValue(), j, subTagInd);
					if (!subTag.isPresent()) break;
					Optional<Integer> subLengthValue = this.getSafeTagIndex(emvData.getValue(), subTagInd, (subTagInd + LENGTH));
					if (!subLengthValue.isPresent()) break;
					Optional<String> subValue = this.getSafeTagValue(emvData.getValue(), (subTagInd + LENGTH), (subTagInd + LENGTH + subLengthValue.get()));
					if (!subValue.isPresent()) break;
					EmvData subEmvData = new EmvData(lengthValue.get(), subValue.get(), subTag.get());
					emvData.getSubEmvs().add(subEmvData);
					j = subTagInd + LENGTH + subLengthValue.get();
				}
				emvDataList.add(emvData);
				i = tagInd + LENGTH + lengthValue.get();
			}
			return emvDataList;
		} catch (Exception e) {
			LOGGER.error("{}", e.getMessage());
			return new ArrayList<>();
		}
	}
}

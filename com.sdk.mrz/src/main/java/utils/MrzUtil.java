package utils;

import enums.MrzFormat;
import enums.MrzSex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.MrzDate;
import model.MrzRange;
import model.MrzRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@Builder
@AllArgsConstructor
public class MrzUtil {

	private String mrzData;
	private String[] mrzRow;
	private MrzFormat format;

	private static final char FILLER = '<';
	private static final String DOUBLE_FILTER = "<<";

	private Logger log = LoggerFactory.getLogger(getClass());

	public MrzUtil(String mrzData) throws Exception {
		this.mrzData = mrzData;
		this.mrzRow = mrzData.split("\n");
		this.format = MrzFormat.get(mrzData);
	}

	public String rawValue(MrzRange... range) {
		final StringBuilder sb = new StringBuilder();
		for (MrzRange r : range) {
			sb.append(mrzRow[r.getIndex()], r.getStartIndex(), r.getEndIndex());
		}
		return sb.toString();
	}

	public String parseString(MrzRange range) throws Exception {
		this.checkValidCharacters(range);
		String str = rawValue(range);
		while (str.endsWith("<")) {
			str = str.substring(0, str.length() - 1);
		}
		return str.replace("" + DOUBLE_FILTER, ", ").replace(FILLER, ' ');
	}

	public void checkValidCharacters(MrzRange range) throws Exception {
		final String str = rawValue(range);
		for (int i = 0; i < str.length(); i++) {
			final char c = str.charAt(i);
			if (c != FILLER && (c < '0' || c > '9') && (c < 'A' || c > 'Z')) {
				throw new Exception("character in MrzData is invalid");
			}
		}
	}

	public MrzRecord parse(){
		try {
			final MrzRecord result = MrzFormat.get(mrzData).newInstance();
			result.fromMrz(mrzData);
			return result;
		}catch (Exception ex){
			log.error("Error parse mrz data: {}", ex.getMessage());
			return null;
		}
	}

	public MrzSex getSex(int col, int row) {
		return MrzSex.fromMrz(mrzRow[row].charAt(col));
	}

	public boolean checkDigit(int col, int row, MrzRange strRange, String fieldName) throws Exception {
		return checkDigit(col, row, rawValue(strRange), fieldName);
	}

	public boolean checkDigit(int col, int row, String str, String fieldName) throws Exception {

		/**
		 * If the check digit validation fails, this will contain the location.
		 */
		MrzRange invalidCheckdigit = null;

		final char digit = (char) (computeCheckDigit(str) + '0');
		char checkDigit = mrzRow[row].charAt(col);
		if (checkDigit == FILLER) {
			checkDigit = '0';
		}
		if (digit != checkDigit) {
			invalidCheckdigit = new MrzRange(col, col + 1, row);
		}
		return invalidCheckdigit == null;
	}

	private static final int[] MRZ_WEIGHTS = new int[]{7, 3, 1};

	public static int computeCheckDigit(String str) throws Exception {
		int result = 0;
		for (int i = 0; i < str.length(); i++) {
			result += getCharacterValue(str.charAt(i)) * MRZ_WEIGHTS[i % MRZ_WEIGHTS.length];
		}
		return result % 10;
	}

	private static int getCharacterValue(char c) throws Exception {
		if (c == FILLER) {
			return 0;
		}
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		if (c >= 'A' && c <= 'Z') {
			return c - 'A' + 10;
		}
		throw new Exception("Invalid character in MRZ record: " + c);
	}

	public MrzDate parseDate(MrzRange range) throws Exception {
		if (range.getLenght() != 6) {
			throw new Exception("Parameter range: invalid value " + range + ": must be 6 characters long");
		}
		MrzRange r;
		r = new MrzRange(range.getStartIndex(), range.getStartIndex() + 2, range.getIndex());
		int year;
		try {
			year = Integer.parseInt(rawValue(r));
		} catch (NumberFormatException ex) {
			year = -1;
		}
		r = new MrzRange(range.getStartIndex() + 2, range.getStartIndex() + 4, range.getIndex());
		int month;
		try {
			month = Integer.parseInt(rawValue(r));
		} catch (NumberFormatException ex) {
			month = -1;
		}
		r = new MrzRange(range.getStartIndex() + 4, range.getStartIndex() + 6, range.getIndex());
		int day;
		try {
			day = Integer.parseInt(rawValue(r));
		} catch (NumberFormatException ex) {
			day = -1;
		}
		return new MrzDate(year, month, day, rawValue(range));
	}

	public String[] parseName(MrzRange range) throws Exception {
		checkValidCharacters(range);
		String str = rawValue(range);
		while (str.endsWith("<")) {
			str = str.substring(0, str.length() - 1);
		}
		final String[] names = str.split(DOUBLE_FILTER);
		String surname = "";
		String givenNames = "";
		if(names.length==1){
			return new String[]{"", names[0]};
		}else if(names.length>1){
			return new String[]{names[1], names[0]};
		}
		return new String[]{surname, givenNames};
	}

}

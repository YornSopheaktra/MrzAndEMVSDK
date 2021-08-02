package model.type;
import enums.MrzFormat;
import lombok.Data;
import model.MrzRange;
import model.MrzRecord;
import utils.MrzUtil;


/**
 * National ID format: A two line long, 36 characters per line format.
 */
@Data
public class MrtdTD2 extends MrzRecord {

	public MrtdTD2(){
		super(MrzFormat.MRTD_TD2);
	}

	private String optional;

	@Override
	public void fromMrz(String mrzData) throws Exception {
		super.fromMrz(mrzData);
		final MrzUtil p = new MrzUtil(mrzData);
		this.setSex(p.getSex(20, 1));
		this.setDocumentNumber(p.parseString(new MrzRange(0, 9, 1)));
		this.setIsValidDocumentNumber(p.checkDigit(9, 1, new MrzRange(0, 9, 1), "document number"));
		this.setDateOfBirth(p.parseDate(new MrzRange(13, 19, 1)));
		this.setExpirationDate(p.parseDate(new MrzRange(21, 27, 1)));
		this.setName(p.parseName(new MrzRange(5, 36, 0)));
		this.setNationality(p.parseString(new MrzRange(15, 18, 1)));
		this.setOptional(p.parseString(new MrzRange(28, 35, 1)));
	}

	@Override
	public String toString() {
		return super.toString();
	}

}

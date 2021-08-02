package model.type;
import enums.MrzFormat;
import model.MrzRange;
import model.MrzRecord;
import utils.MrzUtil;

/**
 * National ID format: A three line long, 30 characters per line format.
 */
public class MrtdTD1 extends MrzRecord {

	public MrtdTD1(){
		super(MrzFormat.MRTD_TD1);
	}

	@Override
	public void fromMrz(String mrzData) throws Exception {
		super.fromMrz(mrzData);
		final MrzUtil p = new MrzUtil(mrzData);
		this.setSex(p.getSex(7, 1));
		this.setDocumentNumber(p.parseString(new MrzRange(5, 14, 0)));
		this.setIsValidDocumentNumber(p.checkDigit(14, 0, new MrzRange(5, 14, 0), "document number"));
		this.setDateOfBirth(p.parseDate(new MrzRange(0, 6, 1)));
		this.setExpirationDate(p.parseDate(new MrzRange(8, 14, 1)));
		this.setName(p.parseName(new MrzRange(0, 30, 2)));
		this.setNationality(p.parseString(new MrzRange(15, 18, 1)));
	}

	@Override
	public String toString() {
		return super.toString();
	}

}

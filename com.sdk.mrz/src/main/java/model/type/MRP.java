package model.type;
import enums.MrzFormat;
import lombok.Data;
import model.MrzRange;
import model.MrzRecord;
import utils.MrzUtil;

/**
 * MRP Passport format: A two line long, 44 characters per line format.
 */

@Data
public class MRP extends MrzRecord {
	private static final long serialVersionUID = 1L;

	public MRP() {
		super(MrzFormat.PASSPORT);
	}

	private String personalNumber;

	private boolean validPersonalNumber;

	@Override
	public void fromMrz(String mrz) throws Exception {
		super.fromMrz(mrz);
		final MrzUtil parser = new MrzUtil(mrz);
		this.setName(parser.parseName(new MrzRange(5, 44, 0)));
		this.setDocumentNumber(parser.parseString(new MrzRange(0, 9, 1)));
		this.setIsValidDocumentNumber(parser.checkDigit(9, 1, new MrzRange(0, 9, 1), "passport number"));
		this.setNationality(parser.parseString(new MrzRange(10, 13, 1)));
		this.setDateOfBirth(parser.parseDate(new MrzRange(13, 19, 1)));
		this.setSex(parser.getSex(20, 1));
		this.setExpirationDate(parser.parseDate(new MrzRange(21, 27, 1)));
		this.setPersonalNumber(parser.parseString(new MrzRange(28, 42, 1)));
		this.setValidPersonalNumber(parser.checkDigit(42, 1, new MrzRange(28, 42, 1), "personal number"));
	}

	@Override
	public String toString() {
		return "MRP{" + super.toString() + ", personalNumber=" + personalNumber + '}';
	}
}

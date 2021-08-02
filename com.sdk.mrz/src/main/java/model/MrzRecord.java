package model;

import enums.MrzDocumentCode;
import enums.MrzFormat;
import enums.MrzSex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.MrzUtil;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MrzRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	private MrzDocumentCode code;
	private char code1;
	private char code2;

	private String issuingCountry;
	private String documentNumber;
	private String nationality;
	private String surname;
	private String givenNames;
	private MrzDate dateOfBirth;
	private MrzSex sex;
	private MrzDate expirationDate;
	private MrzFormat format;

	private Boolean isValidDocumentNumber;

	protected MrzRecord(MrzFormat format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return "MrzRecord{" + "code=" + code + "[" + code1 + code2 + "], issuingCountry=" + issuingCountry + ", documentNumber=" + documentNumber
				+ ", surname=" + surname + ", givenNames=" + givenNames + ", dateOfBirth=" + dateOfBirth + ", sex=" + sex + ", expirationDate="
				+ expirationDate + ", nationality=" + nationality + '}';
	}

	public void fromMrz(String mrzData) throws Exception {
		code = MrzDocumentCode.fromMrz(mrzData);
		code1 = mrzData.charAt(0);
		code2 = mrzData.charAt(1);
		issuingCountry = new MrzUtil(mrzData).parseString(new MrzRange(2,5, 0));
	}

	protected final void setName(String[] name) {
		surname = name[0];
		givenNames = name[1];
	}
}

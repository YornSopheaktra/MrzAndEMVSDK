package enums;

import model.MrzRecord;
import model.type.MRP;
import model.type.MrtdTD1;
import model.type.MrtdTD2;

import java.util.Arrays;
import java.util.List;

public enum MrzFormat {
	MRTD_TD1(3, 30, MrtdTD1.class),
	MRTD_TD2(2, 36 , MrtdTD2.class),
	PASSPORT(2, 44, MRP.class);

	/*,
	FRENCH_ID(2, 36 *//*, FrenchIdCard.class*//*),
	MRV_VISA_B(2, 36 *//*, MrvB.class*//*),

	MRV_VISA_A(2, 44 *//*, MrvA.class*//*),
	,

	SLOVAK_ID_234(2, 34 *//*, SlovakId2_34.class*//*);*/

	private Integer rows;
	private Integer columns;
	private Class<? extends MrzRecord> recordClass;

	MrzFormat(int rows, int columns, Class<? extends MrzRecord> recordClass) {
		this.rows = rows;
		this.columns = columns;
		this.recordClass = recordClass;
	}

	public static MrzFormat get(String mrz) throws Exception {
		//--- check data in each rows mush be the same length
		List<String> rows = Arrays.asList(mrz.split("\n"));
		final int cols = rows.get(0).length();
		if (rows.stream().anyMatch(r -> r.length() != cols)) {
			throw new Exception("Invalid mrz data!");
		}
		return Arrays.stream(values()).filter(format -> format.isFormatOf(rows)).findAny().orElseThrow(() -> new Exception("Invalid format"));
	}

	public boolean isFormatOf(List<String> mrzRows) {
		return rows == mrzRows.size() && columns == mrzRows.get(0).length();
	}

	public final MrzRecord newInstance() throws Exception {
		try {
			return this.recordClass.getDeclaredConstructor().newInstance();
		} catch (Exception ex) {
			throw ex;
		}
	}
}

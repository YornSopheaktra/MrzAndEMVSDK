import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import model.MrzRecord;
import utils.MrzUtil;

public class MrzTest {

	@Test
	public void Test_MrzData_with_NationalID() {
		MrzRecord record = MrzUtil.builder().mrzData("IDKHMO508737576<<<<<<<<<<<<<<<\n9412103M2301262KHM<<<<<<<<<<<0\nSOK<<VEASNA<<<<<<<<<<<<<<<<<<<")
				.build().parse();
		System.out.println(record);
		Assertions.assertNotNull(record);
	}

	@Test
	public void Test_MrzData_with_Passport(){
		MrzRecord record = MrzUtil.builder().mrzData("PNKHMYORN<<SOPHEAKTRA<<<<<<<<<<<<<<<<<<<<<<<\nNOO0046177KHM9211030M2407256N0000008398<<<58")
				.build().parse();
		System.out.println(record);
		Assertions.assertNotNull(record);
	}
}

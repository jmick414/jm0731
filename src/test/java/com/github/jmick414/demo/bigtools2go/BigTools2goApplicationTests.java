package com.github.jmick414.demo.bigtools2go;

import com.github.jmick414.demo.bigtools2go.checkout.CheckoutBuilder;
import com.github.jmick414.demo.bigtools2go.checkout.CheckoutService;
import com.github.jmick414.demo.bigtools2go.checkout.RentalAgreement;
import com.github.jmick414.demo.bigtools2go.tool.Tool;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BigTools2goApplicationTests {

	@Autowired
	private CheckoutService checkoutService;

	@ParameterizedTest(name = "[{index}] - Test 1 - Tool code {0}, Checkout date {1}, Rental days={2}, Discount percent={3}%")
	@MethodSource("test1Args")
	void test1(String toolCode, String checkoutDate, String rentalDays, String discountPercent) {
		Tool actualTool = checkoutService.getTool(toolCode);
		assertEquals("Jackhammer", actualTool.getToolType().getDescription());
		assertEquals("Ridgid", actualTool.getBrand());
		assertEquals(new BigDecimal("2.99"), actualTool.getToolType().getDailyChargeAmount());
		assertTrue(actualTool.getToolType().isWeekdayCharge());
		assertFalse(actualTool.getToolType().isWeekendCharge());
		assertFalse(actualTool.getToolType().isHolidayCharge());

		LocalDate actualCheckoutDate = checkoutService.getCheckoutDate(checkoutDate);
		assertEquals(Month.SEPTEMBER, actualCheckoutDate.getMonth());
		assertEquals(3, actualCheckoutDate.getDayOfMonth());
		assertEquals(2015, actualCheckoutDate.getYear());

		int actualRentalDays = checkoutService.getRentalDays(rentalDays);
		assertEquals(5, actualRentalDays);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			checkoutService.getDiscountPercent(discountPercent);
		});

        assertEquals("Invalid discount percent value; must be in the range of 0-100.", exception.getMessage());

	}

	private static Stream<Arguments> test1Args() {
		return Stream.of(
			Arguments.of("JAKR", "9/3/15", "5", "101")
		);
	}

	@ParameterizedTest(name = "[{index}] - Test 1 - Tool code {0}, Checkout date {1}, Rental days={2}, Discount percent={3}%")
	@MethodSource("test2Args")
	void test2(String toolCode, String checkoutDate, String rentalDays, String discountPercent) {
		Tool actualTool = checkoutService.getTool(toolCode);
		assertEquals("Ladder", actualTool.getToolType().getDescription());
		assertEquals("Werner", actualTool.getBrand());
		assertEquals(new BigDecimal("1.99"), actualTool.getToolType().getDailyChargeAmount());
		assertTrue(actualTool.getToolType().isWeekdayCharge());
		assertTrue(actualTool.getToolType().isWeekendCharge());
		assertFalse(actualTool.getToolType().isHolidayCharge());

		LocalDate actualCheckoutDate = checkoutService.getCheckoutDate(checkoutDate);
		assertEquals(Month.JULY, actualCheckoutDate.getMonth());
		assertEquals(2, actualCheckoutDate.getDayOfMonth());
		assertEquals(2020, actualCheckoutDate.getYear());

		int actualRentalDays = checkoutService.getRentalDays(rentalDays);
		assertEquals(3, actualRentalDays);

		int actualDiscountPercent = checkoutService.getDiscountPercent(discountPercent);
		assertEquals(10, actualDiscountPercent);

		String expectedSummary = """
			Tool code: LADW
			Tool type: Ladder
			Tool brand: Werner
			Rental days: 3
			Checkout date: 07/02/20
			Due date: 07/05/20
			Daily rental charge: $1.99
			Charge days: 2
			Pre-discount charge: $3.98
			Discount percent: 10%
			Discount amount: $0.40
			Final charge: $3.58
			""";

		RentalAgreement rentalAgreement = new RentalAgreement(CheckoutBuilder.newBuilder(actualTool)
				.checkoutDate(actualCheckoutDate)
				.rentalDays(actualRentalDays)
				.discountPercent(actualDiscountPercent)
				.build());

		assertEquals(expectedSummary, rentalAgreement.getSummary());
	}

	private static Stream<Arguments> test2Args() {
		return Stream.of(
				Arguments.of("LADW", "7/2/20", "3", "10")
		);
	}

	@ParameterizedTest(name = "[{index}] - Test 1 - Tool code {0}, Checkout date {1}, Rental days={2}, Discount percent={3}%")
	@MethodSource("test3Args")
	void test3(String toolCode, String checkoutDate, String rentalDays, String discountPercent) {
		Tool actualTool = checkoutService.getTool(toolCode);
		assertEquals("Chainsaw", actualTool.getToolType().getDescription());
		assertEquals("Stihl", actualTool.getBrand());
		assertEquals(new BigDecimal("1.49"), actualTool.getToolType().getDailyChargeAmount());
		assertTrue(actualTool.getToolType().isWeekdayCharge());
		assertFalse(actualTool.getToolType().isWeekendCharge());
		assertTrue(actualTool.getToolType().isHolidayCharge());

		LocalDate actualCheckoutDate = checkoutService.getCheckoutDate(checkoutDate);
		assertEquals(Month.JULY, actualCheckoutDate.getMonth());
		assertEquals(2, actualCheckoutDate.getDayOfMonth());
		assertEquals(2015, actualCheckoutDate.getYear());

		int actualRentalDays = checkoutService.getRentalDays(rentalDays);
		assertEquals(5, actualRentalDays);

		int actualDiscountPercent = checkoutService.getDiscountPercent(discountPercent);
		assertEquals(25, actualDiscountPercent);

		String expectedSummary = """
			Tool code: CHNS
			Tool type: Chainsaw
			Tool brand: Stihl
			Rental days: 5
			Checkout date: 07/02/15
			Due date: 07/07/15
			Daily rental charge: $1.49
			Charge days: 3
			Pre-discount charge: $4.47
			Discount percent: 25%
			Discount amount: $1.12
			Final charge: $3.35
			""";

		RentalAgreement rentalAgreement = new RentalAgreement(CheckoutBuilder.newBuilder(actualTool)
				.checkoutDate(actualCheckoutDate)
				.rentalDays(actualRentalDays)
				.discountPercent(actualDiscountPercent)
				.build());

		assertEquals(expectedSummary, rentalAgreement.getSummary());
	}

	private static Stream<Arguments> test3Args() {
		return Stream.of(
				Arguments.of("CHNS", "7/2/15", "5", "25")
		);
	}

	@ParameterizedTest(name = "[{index}] - Test 1 - Tool code {0}, Checkout date {1}, Rental days={2}, Discount percent={3}%")
	@MethodSource("test4Args")
	void test4(String toolCode, String checkoutDate, String rentalDays, String discountPercent) {
		Tool actualTool = checkoutService.getTool(toolCode);
		assertEquals("Jackhammer", actualTool.getToolType().getDescription());
		assertEquals("DeWalt", actualTool.getBrand());
		assertEquals(new BigDecimal("2.99"), actualTool.getToolType().getDailyChargeAmount());
		assertTrue(actualTool.getToolType().isWeekdayCharge());
		assertFalse(actualTool.getToolType().isWeekendCharge());
		assertFalse(actualTool.getToolType().isHolidayCharge());

		LocalDate actualCheckoutDate = checkoutService.getCheckoutDate(checkoutDate);
		assertEquals(Month.SEPTEMBER, actualCheckoutDate.getMonth());
		assertEquals(3, actualCheckoutDate.getDayOfMonth());
		assertEquals(2015, actualCheckoutDate.getYear());

		int actualRentalDays = checkoutService.getRentalDays(rentalDays);
		assertEquals(6, actualRentalDays);

		int actualDiscountPercent = checkoutService.getDiscountPercent(discountPercent);
		assertEquals(0, actualDiscountPercent);

		String expectedSummary = """
			Tool code: JAKD
			Tool type: Jackhammer
			Tool brand: DeWalt
			Rental days: 6
			Checkout date: 09/03/15
			Due date: 09/09/15
			Daily rental charge: $2.99
			Charge days: 3
			Pre-discount charge: $8.97
			Discount percent: 0%
			Discount amount: $0.00
			Final charge: $8.97
			""";

		RentalAgreement rentalAgreement = new RentalAgreement(CheckoutBuilder.newBuilder(actualTool)
				.checkoutDate(actualCheckoutDate)
				.rentalDays(actualRentalDays)
				.discountPercent(actualDiscountPercent)
				.build());

		assertEquals(expectedSummary, rentalAgreement.getSummary());
	}

	private static Stream<Arguments> test4Args() {
		return Stream.of(
				Arguments.of("JAKD", "9/3/15", "6", "0")
		);
	}

	@ParameterizedTest(name = "[{index}] - Test 1 - Tool code {0}, Checkout date {1}, Rental days={2}, Discount percent={3}%")
	@MethodSource("test5Args")
	void test5(String toolCode, String checkoutDate, String rentalDays, String discountPercent) {
		Tool actualTool = checkoutService.getTool(toolCode);
		assertEquals("Jackhammer", actualTool.getToolType().getDescription());
		assertEquals("Ridgid", actualTool.getBrand());
		assertEquals(new BigDecimal("2.99"), actualTool.getToolType().getDailyChargeAmount());
		assertTrue(actualTool.getToolType().isWeekdayCharge());
		assertFalse(actualTool.getToolType().isWeekendCharge());
		assertFalse(actualTool.getToolType().isHolidayCharge());

		LocalDate actualCheckoutDate = checkoutService.getCheckoutDate(checkoutDate);
		assertEquals(Month.JULY, actualCheckoutDate.getMonth());
		assertEquals(2, actualCheckoutDate.getDayOfMonth());
		assertEquals(2015, actualCheckoutDate.getYear());

		int actualRentalDays = checkoutService.getRentalDays(rentalDays);
		assertEquals(9, actualRentalDays);

		int actualDiscountPercent = checkoutService.getDiscountPercent(discountPercent);
		assertEquals(0, actualDiscountPercent);

		String expectedSummary = """
			Tool code: JAKR
			Tool type: Jackhammer
			Tool brand: Ridgid
			Rental days: 9
			Checkout date: 07/02/15
			Due date: 07/11/15
			Daily rental charge: $2.99
			Charge days: 5
			Pre-discount charge: $14.95
			Discount percent: 0%
			Discount amount: $0.00
			Final charge: $14.95
			""";

		RentalAgreement rentalAgreement = new RentalAgreement(CheckoutBuilder.newBuilder(actualTool)
				.checkoutDate(actualCheckoutDate)
				.rentalDays(actualRentalDays)
				.discountPercent(actualDiscountPercent)
				.build());

		assertEquals(expectedSummary, rentalAgreement.getSummary());
	}

	private static Stream<Arguments> test5Args() {
		return Stream.of(
				Arguments.of("JAKR", "7/2/15", "9", "0")
		);
	}

	@ParameterizedTest
	@MethodSource("test6Args")
	void test6(String toolCode, String checkoutDate, String rentalDays, String discountPercent) {
		Tool actualTool = checkoutService.getTool(toolCode);
		assertEquals("Jackhammer", actualTool.getToolType().getDescription());
		assertEquals("Ridgid", actualTool.getBrand());
		assertEquals(new BigDecimal("2.99"), actualTool.getToolType().getDailyChargeAmount());
		assertTrue(actualTool.getToolType().isWeekdayCharge());
		assertFalse(actualTool.getToolType().isWeekendCharge());
		assertFalse(actualTool.getToolType().isHolidayCharge());

		LocalDate actualCheckoutDate = checkoutService.getCheckoutDate(checkoutDate);
		assertEquals(Month.JULY, actualCheckoutDate.getMonth());
		assertEquals(2, actualCheckoutDate.getDayOfMonth());
		assertEquals(2020, actualCheckoutDate.getYear());

		int actualRentalDays = checkoutService.getRentalDays(rentalDays);
		assertEquals(4, actualRentalDays);

		int actualDiscountPercent = checkoutService.getDiscountPercent(discountPercent);
		assertEquals(50, actualDiscountPercent);

		String expectedSummary = """
			Tool code: JAKR
			Tool type: Jackhammer
			Tool brand: Ridgid
			Rental days: 4
			Checkout date: 07/02/20
			Due date: 07/06/20
			Daily rental charge: $2.99
			Charge days: 1
			Pre-discount charge: $2.99
			Discount percent: 50%
			Discount amount: $1.50
			Final charge: $1.49
			""";

		RentalAgreement rentalAgreement = new RentalAgreement(CheckoutBuilder.newBuilder(actualTool)
				.checkoutDate(actualCheckoutDate)
				.rentalDays(actualRentalDays)
				.discountPercent(actualDiscountPercent)
				.build());

		assertEquals(expectedSummary, rentalAgreement.getSummary());
	}

	private static Stream<Arguments> test6Args() {
		return Stream.of(
				Arguments.of("JAKR", "7/2/20", "4", "50")
		);
	}

}

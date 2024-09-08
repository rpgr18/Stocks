package mvc;


import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

/**
 * Tests for DateRange enum.
 */
public class DateRangeTest {

  @Test
  public void testWeekDateRange() {
    DateRange range = DateRange.WEEK;
    LocalDate startDate = range.getStartDate();
    LocalDate endDate = range.getEndDate();

    assertEquals(LocalDate.now().minusDays(7), startDate);
    assertEquals(LocalDate.now(), endDate);
  }

  @Test
  public void testMonthDateRange() {
    DateRange range = DateRange.MONTH;
    LocalDate startDate = range.getStartDate();
    LocalDate endDate = range.getEndDate();

    assertEquals(LocalDate.now().minusMonths(1), startDate);
    assertEquals(LocalDate.now(), endDate);
  }

  @Test
  public void testYearDateRange() {
    DateRange range = DateRange.YEAR;
    LocalDate startDate = range.getStartDate();
    LocalDate endDate = range.getEndDate();

    assertEquals(LocalDate.now().minusYears(1), startDate);
    assertEquals(LocalDate.now(), endDate);
  }

  @Test
  public void testFiveYearsDateRange() {
    DateRange range = DateRange.FIVE_YEARS;
    LocalDate startDate = range.getStartDate();
    LocalDate endDate = range.getEndDate();

    assertEquals(LocalDate.now().minusYears(5), startDate);
    assertEquals(LocalDate.now(), endDate);
  }

  @Test
  public void testTenYearsDateRange() {
    DateRange range = DateRange.TEN_YEARS;
    LocalDate startDate = range.getStartDate();
    LocalDate endDate = range.getEndDate();

    assertEquals(LocalDate.now().minusYears(10), startDate);
    assertEquals(LocalDate.now(), endDate);
  }

  @Test
  public void testSetCustomWeekRange() {
    LocalDate endDate = LocalDate.of(2024, 6, 10);
    DateRange.setCustomRange(endDate, DateRange.CUSTOM_WEEK);

    assertEquals(LocalDate.of(2024, 6, 3), DateRange.CUSTOM_WEEK.getStartDate());
    assertEquals(LocalDate.of(2024, 6, 10), DateRange.CUSTOM_WEEK.getEndDate());
  }

  @Test
  public void testSetCustomMonthRange() {
    LocalDate endDate = LocalDate.of(2024, 6, 10);
    DateRange.setCustomRange(endDate, DateRange.CUSTOM_MONTH);

    assertEquals(LocalDate.of(2024, 5, 10), DateRange.CUSTOM_MONTH.getStartDate());
    assertEquals(LocalDate.of(2024, 6, 10), DateRange.CUSTOM_MONTH.getEndDate());
  }

  @Test
  public void testSetCustomYearRange() {
    LocalDate endDate = LocalDate.of(2024, 6, 10);
    DateRange.setCustomRange(endDate, DateRange.CUSTOM_YEAR);

    assertEquals(LocalDate.of(2023, 6, 10), DateRange.CUSTOM_YEAR.getStartDate());
    assertEquals(LocalDate.of(2024, 6, 10), DateRange.CUSTOM_YEAR.getEndDate());
  }

  @Test
  public void testSetCustomFiveYearsRange() {
    LocalDate endDate = LocalDate.of(2024, 6, 10);
    DateRange.setCustomRange(endDate, DateRange.CUSTOM_FIVE_YEARS);

    assertEquals(LocalDate.of(2019, 6, 10), DateRange.CUSTOM_FIVE_YEARS.getStartDate());
    assertEquals(LocalDate.of(2024, 6, 10), DateRange.CUSTOM_FIVE_YEARS.getEndDate());
  }

  @Test
  public void testSetCustomTenYearsRange() {
    LocalDate endDate = LocalDate.of(2024, 6, 10);
    DateRange.setCustomRange(endDate, DateRange.CUSTOM_TEN_YEARS);

    assertEquals(LocalDate.of(2014, 6, 10), DateRange.CUSTOM_TEN_YEARS.getStartDate());
    assertEquals(LocalDate.of(2024, 6, 10), DateRange.CUSTOM_TEN_YEARS.getEndDate());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCustomRangeWithInvalidRange() {
    LocalDate endDate = LocalDate.of(2024, 6, 10);
    DateRange.setCustomRange(endDate, DateRange.WEEK);  // This should throw an exception
  }

}

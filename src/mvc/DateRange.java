package mvc;

import java.time.LocalDate;

/**
 * The DateRange enum represents standard date ranges like week, month, year, etc...
 * This class stores a ranges start date (now) and end date (1 week, month, year, etc... ago).
 */
public enum DateRange {
  WEEK(LocalDate.now().minusDays(7), LocalDate.now()),
  MONTH(LocalDate.now().minusMonths(1), LocalDate.now()),
  YEAR(LocalDate.now().minusYears(1), LocalDate.now()),
  FIVE_YEARS(LocalDate.now().minusYears(5), LocalDate.now()),
  TEN_YEARS(LocalDate.now().minusYears(10), LocalDate.now()),
  CUSTOM_WEEK(null, null),
  CUSTOM_MONTH(null, null),
  CUSTOM_YEAR(null, null),
  CUSTOM_FIVE_YEARS(null, null),
  CUSTOM_TEN_YEARS(null, null);


  private LocalDate startDate;
  private LocalDate endDate;

  /**
   * Constructs a DateRange object.
   * @param startDate the start date
   * @param endDate the end date
   */
  DateRange(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * Gets the start date of a range.
   * @return the LocalDate
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Gets the end date of a range.
   * @return the LocalDate
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  /**
   * Sets the start and end date for the CUSTOM range.
   * Can only be a custom week, month, year, etc...
   * @param end the end date
   */
  public static void setCustomRange(LocalDate end, DateRange range) {
    if (end.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("** NICE TRY, THIS PROGRAM CANNOT TELL THE FUTURE XD");
    }

    switch (range) {
      case CUSTOM_WEEK:
        CUSTOM_WEEK.startDate = end.minusWeeks(1);
        CUSTOM_WEEK.endDate = end;
        break;
      case CUSTOM_MONTH:
        CUSTOM_MONTH.startDate = end.minusMonths(1);
        CUSTOM_MONTH.endDate = end;
        break;
      case CUSTOM_YEAR:
        CUSTOM_YEAR.startDate = end.minusYears(1);
        CUSTOM_YEAR.endDate = end;
        break;
      case CUSTOM_FIVE_YEARS:
        CUSTOM_FIVE_YEARS.startDate = end.minusYears(5);
        CUSTOM_FIVE_YEARS.endDate = end;
        break;
      case CUSTOM_TEN_YEARS:
        CUSTOM_TEN_YEARS.startDate = end.minusYears(10);
        CUSTOM_TEN_YEARS.endDate = end;
        break;
      default:
        throw new IllegalArgumentException("** RANGE MUST BE A CUSTOM RANGE **");
    }
  }



}
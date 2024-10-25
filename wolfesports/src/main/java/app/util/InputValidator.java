package app.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class InputValidator {

    public static Scanner in = IO.getScanner();

    // Private constructor
    private InputValidator() {
    }

    public static int promptAndParseInt(String message, int min, int max) {
        try {
            System.out.print(message);
            int input = in.nextInt();
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.print("Please pick a choice between " + min + " and " + max + "...");
                in.next();
            }
        } catch (Exception e) {
            System.out.print("Please pick a valid number...");
            in.next();
            in.next();
        }
        return -1;
    }

    public static int promptAndParseInt(String message) {
        do {
            try {
                System.out.print(message);
                int input = in.nextInt();
                if (input > 0) {
                    return input;
                } else {
                    System.out.print("Please enter a positive non null number");
                    in.next();
                }
            } catch (Exception e) {
                System.out.print("Please pick a valid number...");
                in.next();
                in.next();
            }
        } while (true);
    }

    public static Integer promptAndParseNullableInt(String message) {
        do {
            try {
                System.out.print(message);
                String input = in.next();

                if (input.isEmpty()) {
                    return null;
                }

                Integer parsedInt = Integer.parseInt(input);
                if (parsedInt > 0) {
                    return parsedInt;
                } else {
                    System.out.println("Please enter a positive number.");
                    in.next();
                }
            } catch (NumberFormatException e) {
                System.out.println("Please pick a valid number.");
            }
        } while (true);

    }

    public static Integer promptAndParseTournamentPlaces(String message) {
        do {
            try {
                System.out.print(message);
                int input = in.nextInt();

                if (input > 0) {
                    if (input % 8 == 0) {
                        return input;
                    } else {
                        System.out.println("The number of places must be a positive multiple of 8. Please try again.");
                        in.next();
                    }
                } else {
                    System.out.println("Please enter a positive number.");
                    in.next();
                }
            } catch (NumberFormatException e) {
                System.out.println("Please pick a valid number.");
            }
        } while (true);

    }

    public static Integer promptAndParseNullableTournamentPlaces(String message) {
        do {
            try {
                System.out.print(message);
                String input = in.next();

                if (input.isEmpty()) {
                    return null;
                }

                Integer parsedInt = Integer.parseInt(input);
                if (parsedInt > 0) {
                    if (parsedInt % 8 == 0) {
                        return parsedInt;
                    } else {
                        System.out.println("The number of places must be a positive multiple of 8. Please try again.");
                        in.next();
                    }
                } else {
                    System.out.println("Please enter a positive number.");
                    in.next();
                }
            } catch (NumberFormatException e) {
                System.out.println("Please pick a valid number.");
            }
        } while (true);

    }

    public static Double promptAndParseDouble(String message, double min, double max) {
        do {
            try {
                System.out.print(message);
                Double input = in.nextDouble();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.print("Please pick a choice between " + min + " and " + max + "...");
                    in.next();
                }
            } catch (Exception e) {
                System.out.print("Please pick a valid number...");
                in.next();
                in.next();
            }
        } while (true);
    }

    public static Double promptAndParseDouble(String message) {
        do {
            try {
                System.out.print(message);
                Double input = in.nextDouble();
                if (input >= 0) {
                    return input;
                } else {
                    System.out.print("Please enter a positive number");
                    in.next();
                }
            } catch (Exception e) {
                System.out.print("Please pick a valid number...");
                in.next();
                in.next();
            }
        } while (true);
    }

    public static Double promptAndParseNullableDouble(String message) {
        do {
            try {
                System.out.print(message);
                String input = in.next();

                if (input.isEmpty()) {
                    return null;
                }

                Double parsedDouble = Double.parseDouble(input);
                if (parsedDouble > 0) {
                    return parsedDouble;
                } else {
                    System.out.println("Please enter a positive number.");
                    in.next();
                }
            } catch (NumberFormatException e) {
                System.out.println("Please pick a valid number.");
                in.next();
                in.next();
            }
        } while (true);
    }

    public static String promptAndParseString(String message) {
        do {
            try {
                System.out.print(message);
                String input = in.next();
                if (!input.trim().isEmpty()) {
                    return input;
                } else {
                    System.out.println("Please enter a valid string (no empty strings, spaces, etc.)");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid string.");
                in.next();
            }
        } while (true);
    }

    public static String promptAndParseNullableString(String message) {
        System.out.print(message);
        String input = in.next();
        if (input.trim().isEmpty())
            return null;

        return input;
    }

    public static String promptAndParsePhoneNumber(String message) {
        do {
            try {
                System.out.print(message);
                String input = in.next().trim();

                if (input.matches("\\+?[0-9]{10,15}")) {
                    return input;
                } else {
                    System.out.println(
                            "Please enter a valid phone number (10 to 15 digits, optional + at the beginning).");
                }
            } catch (Exception e) {
                System.out.print("An error occurred. Please enter a valid phone number.");
                in.next();
                in.next();
            }
        } while (true);
    }

    public static LocalDate promptAndParseDate(String message) {
        do {
            try {
                System.out.print(message);
                String dateString = in.next();

                return DateUtils.parseDate(dateString);
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date format. Please enter the date in the format dd-MM-yyyy...");
                in.next();
            } catch (Exception e) {
                System.out.print(e.getMessage());
                in.next();
                in.next();
            }
        } while (true);
    }

    public static LocalDate promptAndParseNullableDate(String message) {
        do {
            try {
                System.out.print(message);
                String dateString = in.next();

                if (dateString.isEmpty())
                    return null;

                return DateUtils.parseDate(dateString);
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date format. Please enter the date in the format dd-MM-yyyy...");
                in.next();
            } catch (Exception e) {
                System.out.print(e.getMessage());
                in.next();
                in.next();
            }
        } while (true);
    }

    public static boolean promptYesOrNo(String message) {
        String input;
        do {
            System.out.print(message + " (y/n): ");
            input = in.next().trim().toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        } while (true);
    }
}

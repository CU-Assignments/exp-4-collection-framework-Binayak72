import java.util.*;

class Card {
    String symbol;
    String value;

    Card(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Symbol: " + symbol + ", Value: " + value;
    }
}

public class CardCollection {
    private static final Collection<Card> cardCollection = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Add Card");
            System.out.println("2. Search Cards by Symbol");
            System.out.println("3. Display All Cards");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCard();
                case 2 -> searchCardsBySymbol();
                case 3 -> displayCards();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addCard() {
        System.out.print("Enter Symbol: ");
        String symbol = scanner.nextLine();
        System.out.print("Enter Value: ");
        String value = scanner.nextLine();
        cardCollection.add(new Card(symbol, value));
        System.out.println("Card added successfully.");
    }

    private static void searchCardsBySymbol() {
        System.out.print("Enter Symbol to search: ");
        String symbol = scanner.nextLine();
        boolean found = false;
        for (Card card : cardCollection) {
            if (card.symbol.equalsIgnoreCase(symbol)) {
                System.out.println(card);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No cards found with symbol: " + symbol);
        }
    }

    private static void displayCards() {
        if (cardCollection.isEmpty()) {
            System.out.println("No cards to display.");
        } else {
            cardCollection.forEach(System.out::println);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deliverableone;

import java.util.ArrayList;




/**
 *
 * @author sidrahmalik!!
 */
public class WarGame extends Game{
    private GroupOfCards deck;
final ArrayList<Card> warPool = new ArrayList<>();
private Player Player1;
private Player Computer;
final int cardsInDeck = 52;
private Player GameWinner = null;
public WarGame() {
super("War");
}

/**
* Creates an ArrayList using the player controlled object and the computer controlled object.
* This method ensures that the game is limited to 1 player and 1 computer.
*
* @param user
*/
public void createPlayerList(Player user) {
Player computer = new WarPlayer("Computer");
ArrayList<Player> playerList = new ArrayList<Player>();
playerList.add(user);
this.Player1 = user;
playerList.add(computer);
this.Computer = computer;
setPlayers(playerList);
}

@Override
public void play() {
System.out.println("");
System.out.println("Starting game!");
// Instantiate GroupOfCards to create deck made up of 52 cards
deck = new GroupOfCards(cardsInDeck);
// Populates the deck of cards, 4 suits and 13 cards for each suit
for (int r = 1; r < 14; r++) {
for (int c = 0; c < 4; c++) {
deck.getCardDeck().add(new Card(r, Card.suits[c]));
}
}
// Call shuffle method from GroupOfCards and shuffle deck
deck.shuffle();
System.out.println("Deck built and shuffled!");
// Create 2 ArrayList's based on the shuffled cards in the deck (one for the user, one for the computer)
ArrayList<Card> userCards = new ArrayList<>();
ArrayList<Card> computerCards = new ArrayList<>();


// Even cards are added to user ArrayList, odd cards are added to computer ArrayList
for (int i = 0; i < deck.getCardDeck().size(); i++) {
if (i % 2 == 0) {
userCards.add(deck.getCardDeck().get(i));
} else {
computerCards.add(deck.getCardDeck().get(i));
}
}
// Create 2 GroupOfCards deck objects using the size of the 2 ArrayLists
GroupOfCards playerDeck = new GroupOfCards(userCards.size());
GroupOfCards computerDeck = new GroupOfCards(computerCards.size());
// Set the cards in these GroupOfCards objects to the ones that were placed in the 2 ArrayLists earlier
playerDeck.setCardDeck(userCards);
computerDeck.setCardDeck(computerCards);
// Give the 2 GroupsOfCards to the player objects
Player1.setCards(playerDeck);
Computer.setCards(computerDeck);
// Clear the userCards ArrayList and the computerCards ArrayList to be used as each players discard pile
userCards.clear();
computerCards.clear();
System.out.println("Deck split and distributed!");
System.out.println("");
// Draw the top card in each players deck
int index = 0;
boolean runGame = true;
while (runGame) {
// When a player runs out of cards, the game ends
if (Player1.getCards().getSize() == 0 || Computer.getCards().getSize() == 0) {
runGame = false;
break;
}
System.out.println("Drawing cards!");
// Draws top card of both players decks
Card playerTopCard = Player1.getCards().getTopCard();
System.out.println(Player1.getName() + " drew: " + playerTopCard.toString());
Card computerTopCard = Computer.getCards().getTopCard();
System.out.println(Computer.getName() + " drew: " + computerTopCard.toString());
System.out.println("");

//Compares the 2 cards that were drawn
System.out.println("Comparing cards!");
Player roundWinner = compareCards(playerTopCard, computerTopCard);
if (roundWinner == Player1) {
System.out.println(Player1.getName() + " wins the hand!");
userCards.add(playerTopCard);
userCards.add(computerTopCard);
} else if (roundWinner == Computer) {
System.out.println(Computer.getName() + " wins the hand!");
computerCards.add(playerTopCard);
computerCards.add(computerTopCard);
} else {
System.out.println("Stalemate! Cards added to pot.");
System.out.println("");
warPool.add(playerTopCard);
warPool.add(computerTopCard);
startWar(userCards, computerCards);
}
System.out.println("");
index++;
}
if (userCards.size() > computerCards.size()) {
GameWinner = Player1;
} else if (userCards.size() < computerCards.size()) {
GameWinner = Computer;
}

}

public Player compareCards(Card playerCard, Card computerCard) {
// Holds the value of each card in a variable so that it can be referenced quickly
int playerCardValue = playerCard.getCardNum();
int computerCardValue = computerCard.getCardNum();
// Checks if the value of the cards are equal
if (playerCardValue != computerCardValue) {
// if they are not equal, the winner will be the player with the higher value card
if (playerCardValue > computerCardValue) {
return Player1;
} else {
return Computer;
}
// if they are equal, the players go to war
} else {
return null;
}
}

public void startWar(ArrayList<Card> userCard, ArrayList<Card> computerCard) {
System.out.println("War has broken out! Each player draws 4 cards.");
if (Player1.getCards().getSize() > 3 && Computer.getCards().getSize() > 3) {
int war = 3; // 3 Cards into the pool
for (int i = 0; i < war; i++) {
warPool.add(Player1.getCards().getTopCard());
warPool.add(Computer.getCards().getTopCard());
}
}
Card playerFinalCard = Player1.getCards().getTopCard();
System.out.println(Player1.getName() + "'s top card is: " + playerFinalCard.toString());
Card computerFinalCard = Computer.getCards().getTopCard();
System.out.println(Computer.getName() + "'s top card is: " + computerFinalCard.toString());
warPool.add(playerFinalCard);
warPool.add(computerFinalCard);
System.out.println("");
Player warWinner = compareCards(playerFinalCard, computerFinalCard);
if (warWinner == Player1) {
System.out.println(Player1.getName() + " wins the war! Pot goes to winner!");
userCard.addAll(warPool);
warPool.clear();
} else if (warWinner == Computer) {
System.out.println(Computer.getName() + " wins the war! Pot goes to winner!");
computerCard.addAll(warPool);
warPool.clear();
} else {
System.out.println("Stalemate! A new war will soon breakout...");
startWar(userCard, computerCard);
}
System.out.println("");
}

@Override
public void declareWinner() {
if (GameWinner == null) {
System.out.println("No winner!");
} else if (GameWinner == Player1) {
System.out.println("Winner is Player 1.");
} else if (GameWinner == Computer) {
System.out.println("Winner is Computer.");
}
}
}


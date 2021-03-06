/**
 * JSkat - A skat program written in Java
 * by Jan Schäfer, Markus J. Luzius and Daniel Loreck
 *
 * Version 0.13.0-SNAPSHOT
 * Copyright (C) 2013-05-10
 *
 * Licensed under the Apache License, Version 2.0. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jskat.ai.nn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jskat.AbstractJSkatTest;
import org.jskat.util.Card;
import org.jskat.util.CardDeck;
import org.jskat.util.CardList;
import org.jskat.util.Player;
import org.junit.Test;

/**
 * Tests for CardDeckSimulator
 */
public class CardDeckSimulatorTest extends AbstractJSkatTest {

	/**
	 * Checks simulation of unknown cards<br />
	 * all cards are unknown
	 */
	@Test
	public void simulateUnknownCards_AllCards() {

		CardList cards = new CardList();

		CardDeck simCards = CardDeckSimulator.simulateUnknownCards(
				Player.FOREHAND, cards);

		assertEquals(32, simCards.size());
	}

	/**
	 * Checks simulation of unknown cards<br />
	 * Fore hand cards are known
	 */
	@Test
	public void simulateUnknownCards_ForeHandKnown() {

		CardList cards = getKnownCards();

		CardDeck simCards = CardDeckSimulator.simulateUnknownCards(
				Player.FOREHAND, cards);

		assertEquals(32, simCards.size());
		assertTrue(cards.contains(simCards.get(0)));
		assertTrue(cards.contains(simCards.get(1)));
		assertTrue(cards.contains(simCards.get(2)));
		assertTrue(cards.contains(simCards.get(11)));
		assertTrue(cards.contains(simCards.get(12)));
		assertTrue(cards.contains(simCards.get(13)));
		assertTrue(cards.contains(simCards.get(14)));
		assertTrue(cards.contains(simCards.get(23)));
		assertTrue(cards.contains(simCards.get(24)));
		assertTrue(cards.contains(simCards.get(25)));
	}

	/**
	 * Checks simulation of unknown cards<br />
	 * Middle hand cards are known
	 */
	@Test
	public void simulateUnknownCards_MiddleHandKnown() {

		CardList cards = getKnownCards();

		CardDeck simCards = CardDeckSimulator.simulateUnknownCards(
				Player.MIDDLEHAND, cards);

		assertEquals(32, simCards.size());
		assertTrue(cards.contains(simCards.get(3)));
		assertTrue(cards.contains(simCards.get(4)));
		assertTrue(cards.contains(simCards.get(5)));
		assertTrue(cards.contains(simCards.get(15)));
		assertTrue(cards.contains(simCards.get(16)));
		assertTrue(cards.contains(simCards.get(17)));
		assertTrue(cards.contains(simCards.get(18)));
		assertTrue(cards.contains(simCards.get(26)));
		assertTrue(cards.contains(simCards.get(27)));
		assertTrue(cards.contains(simCards.get(28)));
	}

	/**
	 * Checks simulation of unknown cards<br />
	 * Rearhand cards are known
	 */
	@Test
	public void simulateUnknownCards_RearHandKnown() {

		CardList cards = getKnownCards();

		CardDeck simCards = CardDeckSimulator.simulateUnknownCards(
				Player.REARHAND, cards);

		assertEquals(32, simCards.size());
		assertTrue(cards.contains(simCards.get(6)));
		assertTrue(cards.contains(simCards.get(7)));
		assertTrue(cards.contains(simCards.get(8)));
		assertTrue(cards.contains(simCards.get(19)));
		assertTrue(cards.contains(simCards.get(20)));
		assertTrue(cards.contains(simCards.get(21)));
		assertTrue(cards.contains(simCards.get(22)));
		assertTrue(cards.contains(simCards.get(29)));
		assertTrue(cards.contains(simCards.get(30)));
		assertTrue(cards.contains(simCards.get(31)));
	}

	private CardList getKnownCards() {
		CardList cards = new CardList();
		cards.add(Card.CJ);
		cards.add(Card.SJ);
		cards.add(Card.HJ);
		cards.add(Card.SJ);
		cards.add(Card.CA);
		cards.add(Card.CT);
		cards.add(Card.CK);
		cards.add(Card.CQ);
		cards.add(Card.C9);
		cards.add(Card.C8);
		return cards;
	}
}

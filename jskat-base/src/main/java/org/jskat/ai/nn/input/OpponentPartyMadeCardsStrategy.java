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
package org.jskat.ai.nn.input;

import java.util.HashSet;
import java.util.Set;

import org.jskat.data.Trick;
import org.jskat.player.PlayerKnowledge;
import org.jskat.util.Card;
import org.jskat.util.Player;

public class OpponentPartyMadeCardsStrategy extends AbstractInputStrategy
		implements InputStrategy {

	@Override
	public int getNeuronCount() {

		return 32;
	}

	@Override
	public double[] getNetworkInput(PlayerKnowledge knowledge, Card cardToPlay) {

		double[] result = getEmptyInputs();

		Set<Player> partyMembers = getPartyMembers(knowledge);

		for (Trick trick : knowledge.getCompletedTricks()) {
			if (!partyMembers.contains(trick.getTrickWinner())) {
				// trick was won by opponent's party
				for (Card card : trick.getCardList()) {
					result[getNetworkInputIndex(card)] = 1.0;
				}
			}
		}

		return result;
	}

	protected Set<Player> getPartyMembers(PlayerKnowledge knowledge) {

		Set<Player> result = new HashSet<Player>();
		if (knowledge.getDeclarer().equals(knowledge.getPlayerPosition())) {
			// player is declarer
			result.add(knowledge.getPlayerPosition());
		} else {
			// player is opponent
			result.add(knowledge.getDeclarer().getLeftNeighbor());
			result.add(knowledge.getDeclarer().getRightNeighbor());
		}
		return result;
	}

	protected static int getNetworkInputIndex(final Card card) {

		return card.getSuit().getSuitOrder() * 8 + card.getNullOrder();
	}
}

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
package org.jskat.util.rule;

import org.jskat.util.Card;
import org.jskat.util.CardList;
import org.jskat.util.GameType;

/**
 * Implementation of skat rules for Grand games
 */
public class GrandRule extends SuitGrandRule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMultiplier(CardList cards, GameType gameType) {
		if (gameType != GameType.GRAND) {
			throw new IllegalArgumentException("Wrong ruleset - " + gameType);
		}
		int result = 1;
		if (cards.contains(Card.CJ)) {
			result++;
			if (cards.contains(Card.SJ)) {
				result++;
				if (cards.contains(Card.HJ)) {
					result++;
					if (cards.contains(Card.DJ)) {
						result++;
					}
				}
			}
		} else {
			result++;
			if (!cards.contains(Card.SJ)) {
				result++;
				if (!cards.contains(Card.HJ)) {
					result++;
					if (!cards.contains(Card.DJ)) {
						result++;
					}
				}
			}
		}
		return result;
	}
}

/*

@ShortLicense@

Authors: @JS@

Released: @ReleaseDate@

 */

package de.jskat.ai.nn.train;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jskat.ai.IJSkatPlayer;
import de.jskat.ai.nn.AIPlayerNN;
import de.jskat.ai.nn.data.SkatNetworks;
import de.jskat.ai.nn.util.NeuralNetwork;
import de.jskat.control.JSkatMaster;
import de.jskat.control.JSkatThread;
import de.jskat.control.SkatGame;
import de.jskat.data.GameAnnouncement;
import de.jskat.data.SkatGameData.GameState;
import de.jskat.gui.NullView;
import de.jskat.util.CardDeck;
import de.jskat.util.GameType;
import de.jskat.util.Player;

/**
 * Trains the neural networks
 */
public class NNTrainer extends JSkatThread {

	private static Log log = LogFactory.getLog(NNTrainer.class);

	private JSkatMaster jskat;

	private Random rand;
	private List<StringBuffer> nullGames;

	private GameType gameType;

	/**
	 * Constructor
	 */
	public NNTrainer() {

		jskat = JSkatMaster.instance();

		rand = new Random();
		nullGames = new ArrayList<StringBuffer>();

		initLearningPatterns();
	}

	private void initLearningPatterns() {

		// test a perfect null game
		StringBuffer buffer = new StringBuffer();
		buffer.append("CA CT CK "); // 3 cards fore hand //$NON-NLS-1$
		buffer.append("C9 ST SK "); // 3 cards middle hand //$NON-NLS-1$
		buffer.append("H7 DA DT "); // 3 cards hind hand //$NON-NLS-1$
		buffer.append("SA HA "); // skat //$NON-NLS-1$
		buffer.append("CQ CJ C8 C7 "); // 4 cards fore hand //$NON-NLS-1$
		buffer.append("SQ SJ HK HQ "); // 4 cards middle hand //$NON-NLS-1$
		buffer.append("DK DQ D9 D8 "); // 4 cards hind hand //$NON-NLS-1$
		buffer.append("S9 S8 S7 "); // 3 cards fore hand //$NON-NLS-1$
		buffer.append("HJ H9 H8 "); // 3 cards middle hand //$NON-NLS-1$
		buffer.append("D7 HT DJ"); // 3 cards hind hand //$NON-NLS-1$
		nullGames.add(buffer);

		buffer = new StringBuffer();
		buffer.append("CA CT CK "); // 3 cards fore hand //$NON-NLS-1$
		buffer.append("S9 ST SK "); // 3 cards middle hand //$NON-NLS-1$
		buffer.append("H7 DA DT "); // 3 cards hind hand //$NON-NLS-1$
		buffer.append("SA HA "); // skat //$NON-NLS-1$
		buffer.append("CQ CJ C8 C7 "); // 4 cards fore hand //$NON-NLS-1$
		buffer.append("SQ SJ HK HQ "); // 4 cards middle hand //$NON-NLS-1$
		buffer.append("DK DQ D9 D8 "); // 4 cards hind hand //$NON-NLS-1$
		buffer.append("C9 S8 S7 "); // 3 cards fore hand //$NON-NLS-1$
		buffer.append("HJ H9 H8 "); // 3 cards middle hand //$NON-NLS-1$
		buffer.append("D7 HT DJ"); // 3 cards hind hand //$NON-NLS-1$
		nullGames.add(buffer);

		buffer = new StringBuffer();
		buffer.append("C9 C8 C7 "); // 3 cards fore hand //$NON-NLS-1$
		buffer.append("CA CT CK "); // 3 cards middle hand //$NON-NLS-1$
		buffer.append("CQ CJ SA "); // 3 cards hind hand //$NON-NLS-1$
		buffer.append("SJ SK "); // skat //$NON-NLS-1$
		buffer.append("ST S9 S8 S7 "); // 4 cards fore hand //$NON-NLS-1$
		buffer.append("SQ HA HT HK "); // 4 cards middle hand //$NON-NLS-1$
		buffer.append("HQ DA DT DK "); // 4 cards hind hand //$NON-NLS-1$
		buffer.append("H9 H8 H7 "); // 3 cards fore hand //$NON-NLS-1$
		buffer.append("DQ DJ D9 "); // 3 cards middle hand //$NON-NLS-1$
		buffer.append("D8 D7 HJ"); // 3 cards hind hand //$NON-NLS-1$
		nullGames.add(buffer);

		buffer = new StringBuffer();
		buffer.append("SA ST SK "); // 3 cards fore hand //$NON-NLS-1$
		buffer.append("S9 CT CK "); // 3 cards middle hand //$NON-NLS-1$
		buffer.append("H7 DA DT "); // 3 cards hind hand //$NON-NLS-1$
		buffer.append("CA HA "); // skat //$NON-NLS-1$
		buffer.append("SQ SJ S8 S7 "); // 4 cards fore hand //$NON-NLS-1$
		buffer.append("CQ CJ HK HQ "); // 4 cards middle hand //$NON-NLS-1$
		buffer.append("DK DQ D9 D8 "); // 4 cards hind hand //$NON-NLS-1$
		buffer.append("C9 C8 C7 "); // 3 cards fore hand //$NON-NLS-1$
		buffer.append("HJ H9 H8 "); // 3 cards middle hand //$NON-NLS-1$
		buffer.append("D7 HT DJ"); // 3 cards hind hand //$NON-NLS-1$
		nullGames.add(buffer);

		buffer = new StringBuffer();
		buffer.append("HA HT HK "); // 3 cards fore hand //$NON-NLS-1$
		buffer.append("H9 CT CK "); // 3 cards middle hand //$NON-NLS-1$
		buffer.append("S7 DA DT "); // 3 cards hind hand //$NON-NLS-1$
		buffer.append("CA SA "); // skat //$NON-NLS-1$
		buffer.append("HQ HJ H8 H7 "); // 4 cards fore hand //$NON-NLS-1$
		buffer.append("CQ CJ SK SQ "); // 4 cards middle hand //$NON-NLS-1$
		buffer.append("DK DQ D9 D8 "); // 4 cards hind hand //$NON-NLS-1$
		buffer.append("S9 S8 S7 "); // 3 cards fore hand //$NON-NLS-1$
		buffer.append("SJ S9 S8 "); // 3 cards middle hand //$NON-NLS-1$
		buffer.append("D7 ST DJ"); // 3 cards hind hand //$NON-NLS-1$
		nullGames.add(buffer);
	}

	/**
	 * Sets the game type to learn
	 * 
	 * @param newGameType
	 *            Game type
	 */
	public void setGameType(GameType newGameType) {

		gameType = newGameType;
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

		trainNets();
	}

	/**
	 * Trains the neural networks
	 */
	private void trainNets() {

		IJSkatPlayer nnPlayer1 = new AIPlayerNN();
		((AIPlayerNN) nnPlayer1).setIsLearning(true);
		IJSkatPlayer nnPlayer2 = new AIPlayerNN();
		((AIPlayerNN) nnPlayer2).setIsLearning(true);
		IJSkatPlayer nnPlayer3 = new AIPlayerNN();
		((AIPlayerNN) nnPlayer3).setIsLearning(true);
		NeuralNetwork declarerNet = SkatNetworks.getNetwork(gameType, true);
		NeuralNetwork opponentNet = SkatNetworks.getNetwork(gameType, false);
		double avgDeclDiff = 0.0d;
		double avgOppDiff = 0.0d;

		long episodes = 0;
		long episodesWonGames = 0;
		long totalWonGames = 0;
		long totalGames = 0;
		int episodeSteps = 100;

		while (true) {

			if (episodes > 0 && episodes % episodeSteps == 0) {

				log.debug(gameType + ": Episode " + episodes + " won games " //$NON-NLS-1$ //$NON-NLS-2$
						+ episodesWonGames + " (" + 100 * episodesWonGames //$NON-NLS-1$
						/ (episodeSteps * 3) + " %)" + " total won games " //$NON-NLS-1$ //$NON-NLS-2$
						+ totalWonGames + " (" + 100 * totalWonGames //$NON-NLS-1$
						/ totalGames + " %)"); //$NON-NLS-1$
				log.debug("        Declarer net: " //$NON-NLS-1$
						+ declarerNet.getIterations()
						+ " iterations " //$NON-NLS-1$
						+ Math.round(avgDeclDiff * 10000.0d)
						/ 10000.d
						+ " avg diff"); //$NON-NLS-1$
				log.debug("        Opponent net: " //$NON-NLS-1$
						+ opponentNet.getIterations()
						+ " iterations " //$NON-NLS-1$
						+ Math.round(avgOppDiff * 10000.0d)
						/ 10000.d
						+ " avg diff"); //$NON-NLS-1$

				jskat.addTrainingResult(gameType, episodes, totalWonGames,
						episodesWonGames, avgDeclDiff, avgOppDiff);

				episodesWonGames = 0;
			}

			for (Player currPlayer : Player.values()) {

				SkatGame game = new SkatGame(null, nnPlayer1, nnPlayer2,
						nnPlayer3);
				game.setView(new NullView());
				game.setMaxSleep(0);

				// CardDeck deck = new CardDeck(nullGames.get(
				// rand.nextInt(nullGames.size())).toString());
				CardDeck deck = new CardDeck();
				deck.shuffle();
				log.debug("Card deck: " + deck); //$NON-NLS-1$
				game.setCardDeck(deck);
				game.dealCards();

				game.setSinglePlayer(currPlayer);

				GameAnnouncement ann = new GameAnnouncement();
				// ann.setGameType(GameTypes.NULL);
				ann.setGameType(gameType);
				game.setGameAnnouncement(ann);

				game.setGameState(GameState.TRICK_PLAYING);

				game.start();
				try {
					game.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (game.isGameWon()) {

					episodesWonGames++;
					totalWonGames++;
				}

				totalGames++;
				avgDeclDiff = (avgDeclDiff * (totalGames - 1) + declarerNet
						.getAvgDiff()) / totalGames;
				avgOppDiff = (avgOppDiff * (totalGames - 1) + opponentNet
						.getAvgDiff()) / totalGames;
			}

			episodes++;

			checkWaitCondition();
		}
	}
}

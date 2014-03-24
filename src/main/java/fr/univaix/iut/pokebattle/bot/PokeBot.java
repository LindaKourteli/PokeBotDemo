package fr.univaix.iut.pokebattle.bot;

import fr.univaix.iut.pokebattle.smartcell.PokeAvecEleveur;
import fr.univaix.iut.pokebattle.smartcell.PokemonCriesCell;
import fr.univaix.iut.pokebattle.smartcell.SmartCell;
import fr.univaix.iut.pokebattle.twitter.Tweet;



public class PokeBot implements Bot {
	    
	/**
     * List of smartcell the questions go through to
     * find an answer.
     */
	
    private final SmartCell[] smartCells = new SmartCell[]{
            new PokemonCriesCell(),
            new PokeAvecEleveur(),
    };

    /**
     * Ask something to Bot, it will respond to you.
     *
     * @param question The question you ask.
     * @return An answer... or null if it doesn't get it.
     */
    
    @Override
    public String ask(Tweet question) {
        for (SmartCell cell : smartCells) {
            String answer = cell.ask(question);  
            if (answer != null) {
                return answer;
            }
        }
        return null;
    }

}













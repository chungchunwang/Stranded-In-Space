package TextGameEngine;

/**
 * An interface to enable a end game lambda function to be passed as a parameter.
 */
public interface TGE_F_EndGame {
    /**
     * @param endScreen The end screen to be shown.
     */
    void call(int endScreen);
}

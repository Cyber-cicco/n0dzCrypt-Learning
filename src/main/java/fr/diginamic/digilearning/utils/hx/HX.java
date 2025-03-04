package fr.diginamic.digilearning.utils.hx;

/**
 * Collection of Reponse headers from HTMX
 */
public class HX {
    // Response headers
    /**can be used to do a client-side redirect to a new location*/
    public static final String REDIRECT = "HX-Redirect";
    /**	pushes a new url into the history stack*/
    public static final String PUSH_URL = "HX-Push-Url";
    /**	a CSS selector that updates the target of the content update to a different element on the page*/
    public static final String RETARGET = "HX-Retarget";
    /**	allows you to specify how the response will be swapped.*/
    public static final String RESWAP = "HX-Reswap";
    /**	allows you to do a client-side redirect that does not do a full page reload*/
    public static final String LOCATION = "HX-Location";
    /**	if set to “true” the client-side will do a full refresh of the page*/
    public static final String REFRESH = "HX-Refresh";
    /**	replaces the current URL in the location bar*/
    public static final String REPLACE_URL = "HX-Replace-Url";
    /**a CSS selector that allows you to choose which part of the response is used to be swapped in.
     *  Overrides an existing hx-select on the triggering element*/
    public static final String RESELECT = "HX-Reselect";
    /**	allows you to trigger client-side events*/
    public static final String TRIGGER = "HX-Trigger";
    /**allows you to trigger client-side events after the settle step*/
    public static final String TRIGGER_AFTER_SWAP = "HX-Trigger-After-Swap";
    /**allows you to trigger client-side events after the swap step*/
    public static final String TRIGGER_AFTER_SETTLE = "HX-Trigger-After-Settle";
}

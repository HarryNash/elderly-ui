package g1736229.elderlyui;

import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading;

import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H1;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H2;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H3;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H4;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H5;
import static com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapHeading.H6;

/**
 * Created by Evche on 15/11/2017.
 */

public class Headings {
    public DefaultBootstrapHeading getCorrespondingHeadingClass(String headingInString){
        switch(headingInString){
            case "h1":
                return H1;
            case "h2":
                return H2;
            case "h3":
                return H3;
            case "h4":
                return H4;
            case "h5":
                return H5;
            case "h6":
                return H6;
        }
        return null;
    }
}

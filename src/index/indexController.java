package index;

import com.jfinal.core.Controller;

public class indexController extends Controller {

    public void index() {
        render("fileQuery.html");
    }
}

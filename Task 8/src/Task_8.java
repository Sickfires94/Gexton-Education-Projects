import java.io.File;
import java.util.Iterator;

import org.lexvo.uwn.Entity;
import org.lexvo.uwn.Statement;
import org.lexvo.uwn.UWN;

public class Task_8 {
    public static void main(String[] args) throws Exception {
        UWN uwn = new UWN(new File("./plugins/"));

        Iterator<Statement> it = uwn.getMeanings(Entity.createTerm("abduct", "eng"));
        it.next();
        Statement meaningStatement = it.next();
        Entity meaning = meaningStatement.getObject();

        Iterator<Statement> word = uwn.get(meaning);
        while (word.hasNext())
            System.out.println(word.next());
    }
}

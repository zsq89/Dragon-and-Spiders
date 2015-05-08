package Menu;

import Collision.*;

import java.awt.*;
import java.awt.image.ImageObserver;

public class EndMenu extends GameMenu{

    public EndMenu(String ttl, String[] btns){
        super(ttl, btns);
    }

    @Override
    public void render(Graphics g, ImageObserver ob){
        super.render(g,ob);
        addTitle(g,titleColor);
        for (int i=0;i<buttons.size();i++ ) {
            int[] bound=addButton(g, buttons.get(i), (Board.B_HEIGHT*2/5+i*60));
            if (bounds.size()<buttons.size()) bounds.add(bound);
        }

    }

}
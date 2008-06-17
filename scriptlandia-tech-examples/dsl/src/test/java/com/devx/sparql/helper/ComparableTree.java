package com.devx.sparql.helper;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.CommonToken;

public class ComparableTree extends CommonTree {
    public ComparableTree() {
        super();
    }

    public ComparableTree( CommonToken token ) {
        super( token );
    }

    public boolean equals( Object other ) {
        if (other == null || !( other instanceof Tree ))
            return false;

        Tree that = (Tree) other;

        if (this.getType() != that.getType() || !this.getText().equals( that.getText() ) || this.getChildCount() != that.getChildCount())
            return false;

        for (int i = 0; i < this.getChildCount(); i++) {
            if (!this.getChild( i ).equals( that.getChild( i ) ))
                return false;
        }

        return true;
    }

    public int hashCode() {
        return getType() ^ ( getText() != null ? getText().hashCode() : 0 );
    }
}

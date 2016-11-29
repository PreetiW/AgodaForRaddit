// Generated code from Butter Knife. Do not modify!
package com.sample.agora.util;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class PostsRecylerAdapter$ViewHolder$$ViewBinder<T extends PostsRecylerAdapter.ViewHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131493013, "field 'rowContainer' and method 'gridItemClicked'");
    target.rowContainer = finder.castView(view, 2131493013, "field 'rowContainer'");
    unbinder.view2131493013 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.gridItemClicked();
      }
    });
    view = finder.findRequiredView(source, 2131493014, "field 'postImage'");
    target.postImage = finder.castView(view, 2131493014, "field 'postImage'");
    view = finder.findRequiredView(source, 2131493015, "field 'postTitle'");
    target.postTitle = finder.castView(view, 2131493015, "field 'postTitle'");
    view = finder.findRequiredView(source, 2131493016, "field 'postText'");
    target.postText = finder.castView(view, 2131493016, "field 'postText'");
    view = finder.findRequiredView(source, 2131493017, "field 'postVotes'");
    target.postVotes = finder.castView(view, 2131493017, "field 'postVotes'");
    view = finder.findRequiredView(source, 2131493018, "field 'postTime'");
    target.postTime = finder.castView(view, 2131493018, "field 'postTime'");
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends PostsRecylerAdapter.ViewHolder> implements Unbinder {
    private T target;

    View view2131493013;

    protected InnerUnbinder(T target) {
      this.target = target;
    }

    @Override
    public final void unbind() {
      if (target == null) throw new IllegalStateException("Bindings already cleared.");
      unbind(target);
      target = null;
    }

    protected void unbind(T target) {
      view2131493013.setOnClickListener(null);
      target.rowContainer = null;
      target.postImage = null;
      target.postTitle = null;
      target.postText = null;
      target.postVotes = null;
      target.postTime = null;
    }
  }
}

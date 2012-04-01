package com.highexplosive.client;

import greendroid.app.GDApplication;
import android.content.Intent;
 
public class HxApplication extends GDApplication {
    @Override
   public Class<?> getHomeActivityClass() {
       return NewsActivity.class;
   }
 
   @Override
   public Intent getMainApplicationIntent() {
       return new Intent(Intent.ACTION_DEFAULT);
   }
}
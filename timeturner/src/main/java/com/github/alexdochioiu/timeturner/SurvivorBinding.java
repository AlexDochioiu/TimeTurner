package com.github.alexdochioiu.timeturner;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by Alexandru Iustin Dochioiu on 11-Feb-19
 */
public class SurvivorBinding {

    private static final HashMap<String, InstanceSaver> saverMap = new HashMap<>();

    public static <Y> void bind(final Y object, final Lifecycle lifecycle) {
        final String className = object.getClass().getName();

        //noinspection unchecked
        final InstanceSaver<Y> saver = saverMap.remove(className);

        if (saver != null) {
            saver.restore(object);
        }

        lifecycle.addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            void onDestroy() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
                //noinspection unchecked,ConstantConditions
                Constructor<InstanceSaver<Y>> instanceSaverConstructor = (Constructor<InstanceSaver<Y>>) object.getClass().getClassLoader()
                        .loadClass(className + "_Binding")
                        .getConstructor(object.getClass());

                //noinspection unchecked
                InstanceSaver<Y> saverInstance = instanceSaverConstructor.newInstance(object);
                saverMap.put(className, saverInstance);
            }
        });
    }

    public abstract static class InstanceSaver<T> {
        protected abstract void restore(final T tClass);
    }
}

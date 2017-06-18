package com.hkv.melon.space.interfaces;

/**
 * Created by Melon on 09.06.2017.
 */

public interface
IParser <T1, T2> {
    public T1 parse(T2 jsonString);
}

package com.panda.common.constant;



//定义了一个常量类MovieRankingList
// 包含一个静态数组listNames，用于存储电影排行榜的名称。
// listNames：定义了一个包含3个元素的静态数组，表示电影排行榜的名称。
// 第1个元素为总票房排行榜的名称，
// 第2个元素为国内票房排行榜的名称，
// 第3个元素为国外票房排行榜的名称。
// static块：使用静态块初始化listNames数组，将各个排行榜的名称存储到数组中。
// 静态块在类加载时执行，可以保证数组在类第一次使用前被初始化。

public class MovieRankingList {

    public static final String[] listNames = new String[3];

    static {
        listNames[0] = "totalBoxOfficeList";
        listNames[1] = "domesticBoxOfficeList";
        listNames[2] = "foreignBoxOfficeList";
    }

}

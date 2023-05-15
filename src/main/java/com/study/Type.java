package com.study;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Type {
	//GENDERSマップのキー（K）はInteger、値（V）はString
	public static final Map<Integer, String> GENDERS;
	static {
		//								LinkedHashMapクラスは格納された順番を保持
		Map<Integer, String> genders = new LinkedHashMap<>();
		//Map.putでIntegerにStringを対応付け
		genders.put(0, "選択しない");
		genders.put(1, "男性");
		genders.put(2, "女性");
		genders.put(3, "その他");
		//Collections.unmodifiableMapでgendersマップをラップしてGENDERSは変更不可能になる
		GENDERS = Collections.unmodifiableMap(genders);
	}

	public static final Map<Integer, String> DEPARTMENTS;
	static {
		Map<Integer, String> departments = new LinkedHashMap<>();
		departments.put(0, "開発");
		departments.put(1, "営業");
		departments.put(2, "人事");
		departments.put(3, "経理");
		DEPARTMENTS = Collections.unmodifiableMap(departments);
	}
}

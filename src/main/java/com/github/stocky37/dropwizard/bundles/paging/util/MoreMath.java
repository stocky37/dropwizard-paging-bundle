package com.github.stocky37.dropwizard.bundles.paging.util;

public interface MoreMath {
	static long ceilingDivide(long a, long b) {
		return (a - 1) / b + 1;
	}

	static int ceilingDivide(int a, int b) {
		return (a - 1) / b + 1;
	}
}

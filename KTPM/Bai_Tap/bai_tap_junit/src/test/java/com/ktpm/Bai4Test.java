package com.ktpm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class Bai4Test {

	// TCO1
    @Test
    public void TCO1() {
        int[] a = {};
        int n = 0;
        int[] b = {};
        int m = 0;
        int[] c = new int[0];

        Bai4.cal(a, n, b, m, c);

        assertArrayEquals(new int[] {}, c);
    }

    // TCO2
    @Test
    public void TCO2() {
        int[] a = {};
        int n = 0;
        int[] b = {2};
        int m = 1;
        int[] c = new int[1];

        Bai4.cal(a, n, b, m, c);

        assertArrayEquals(new int[] {2}, c);
    }

    // TCO3
    @Test
    public void TCO3() {
        int[] a = {1};
        int n = 1;
        int[] b = {};
        int m = 0;
        int[] c = new int[1];

        Bai4.cal(a, n, b, m, c);

        assertArrayEquals(new int[] {1}, c);
    }

    // TCO4
    @Test
    public void TCO4() {
        int[] a = {1, 2};
        int n = 2;
        int[] b = {2, 3};
        int m = 2;
        int[] c = new int[n + m];

        Bai4.cal(a, n, b, m, c);

        assertArrayEquals(new int[] {1, 2, 2, 3}, c);
    }

    // TCO5
    @Test
    public void TCO5() {
        int[] a = {2, 3};
        int n = 2;
        int[] b = {1, 4};
        int m = 2;
        int[] c = new int[n + m];

        Bai4.cal(a, n, b, m, c);

        assertArrayEquals(new int[] {1, 2, 3, 4}, c);
    }

    // TCO6
    @Test
    public void TCO6() {
        int[] a = {1, 2, 3, 4};
        int n = 4;
        int[] b = {};
        int m = 0;
        int[] c = new int[n + m];

        Bai4.cal(a, n, b, m, c);

        assertArrayEquals(new int[] {1, 2, 3, 4}, c);
    }
    // TC07
    @Test
    public void TC07() {
        int[] a = {3};
        int n = 1;
        int[] b = {1};
        int m = 1;
        int[] c = new int[n + m];

        Bai4.cal(a, n, b, m, c);

        assertArrayEquals(new int[] {1, 3}, c);
    }

    // TC08
    @Test
    public void TC08() {
        int[] a = {2, 3};
        int n = 2;
        int[] b = {0, 1};
        int m = 2;
        int[] c = new int[n + m];

        Bai4.cal(a, n, b, m, c);

        assertArrayEquals(new int[] {0, 1, 2, 3}, c);
    }

    // TC09
    @Test
    public void TC09() {
        int[] a = {0, 1, 2, 3};
        int n = 4;
        int[] b = {5, 6, 7, 9};
        int m = 4;
        int[] c = new int[n + m];

        Bai4.cal(a, n, b, m, c);

        assertArrayEquals(new int[] {0, 1, 2, 3, 5, 6, 7, 9}, c);
    }

    // TC10
    @Test
    public void TC10() {
        int[] a = {0, 1, 2};
        int n = 3;
        int[] b = {3, 4};
        int m = 2;
        int[] c = new int[n + m];

        Bai4.cal(a, n, b, m, c);

        assertArrayEquals(new int[] {0, 1, 2, 3, 4}, c);
    }

}
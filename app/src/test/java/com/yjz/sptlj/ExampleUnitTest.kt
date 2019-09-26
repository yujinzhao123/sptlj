package com.yjz.sptlj

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        var t :A = B();
        t.tt()
    }

    open class A{
        open fun tt(){
            System.out.println("aaaaa")
        }
    }

    class B : A(){
        override fun tt() {
            super.tt()
            System.out.println("BBBBB")
        }
    }
}

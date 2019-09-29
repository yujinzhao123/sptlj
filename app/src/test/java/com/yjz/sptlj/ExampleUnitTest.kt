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

        var count = 2//正式作为上的人数


        //测试用的，没到4个人 创建假玩家
        for (i in count..3) {

            System.out.println("iii:"+i)

        }

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

package com.example.shoppinglist.Di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel >)



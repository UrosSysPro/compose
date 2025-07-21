package net.systemvi.configurator.utils.syntax

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.some

fun <A,B> Pair<Option<A>,Option<B>>.sequence(): Option<Pair<A,B>> =
    if(this.first.isSome()&&this.second.isSome())
        Pair(
            (this.first as Some<A>).value,
            (this.second as Some<B>).value,
        ).some()
    else
        None
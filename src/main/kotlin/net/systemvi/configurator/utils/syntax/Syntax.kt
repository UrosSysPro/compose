package net.systemvi.configurator.utils.syntax

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.some

fun <A,B> Pair<Option<A>,Option<B>>.sequence(): Option<Pair<A,B>> = this.paired()

fun <A,B> Pair<Option<A>,Option<B>>.paired(): Option<Pair<A,B>> =
    if(this.first.isSome()&&this.second.isSome())
        Pair(
            (this.first as Some<A>).value,
            (this.second as Some<B>).value,
        ).some()
    else
        None

fun <A,B,C> Triple<Option<A>,Option<B>,Option<C>>.tripled(): Option<Triple<A,B,C>> =
    if(
        this.first.isSome()
        && this.second.isSome()
        && this.third.isSome()
    )
        Triple(
            (this.first as Some<A>).value,
            (this.second as Some<B>).value,
            (this.third as Some<C>).value,
        ).some()
    else
        None

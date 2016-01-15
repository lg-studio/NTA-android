package com.zl.android.domain;

/**
 * Super class for all use cases implementations.
 *
 * <br>
 *     Use cases receive commands from Presenters. While execute commands, they leverage
 *     Repositories functions.
 *
 * <br>
 *     Implementing classes should not use Android specific functions, but just functions
 *     available through repositories. If some functions are missing, use case implementation
 *     can define a new repository interface and implement those functions there. Repositories
 *     are allowed to use what even API's they need.
 */
public abstract class UseCase {

}

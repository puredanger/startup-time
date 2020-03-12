# Demo of dev compilation to improve startup time

This project is a simple demonstration of how to compile your dependencies to improve dev startup performance, see: https://clojure.org/guides/dev_startup_time

Files:

* `deps.edn` - the project dependencies, mimicking simple web deps (you probably have more)
* `src/example.clj` - sample app (extra stuff required as an example)
* `dev/user.clj` - user.clj used only in dev

The deps.edn declares an extra alias `:dev` that we'll use only at dev time - it includes the dev/ directory and classes/ directories on the classpath. user.clj sets up the `user` namespace with our app and its dependencies, similar to what you'd do in the "reloaded" pattern. It would probably also have functions to start your server or whatever is useful in your app.

Typically you'd start your REPL or server while using the dev alias:

```shell
clj -A:dev
```

If we want to check how long that startup takes we can simulate it like this:

```shell
time clj -A:dev -e nil
```

If that did any downloading, run it again to get a better before timing.

To AOT compile your dev dependencies and keep them around:

```shell
clojure -A:dev -e "(binding [*compile-files* true] (require 'user :reload-all))"
```

Basically, reload the user namespace and (transitively) compile the classes you load into the classes/ directory.

Now you can retry the dev startup time (note again that the :dev alias adds the classes/ directory to the classpath:

```shell
time clj -A:dev -e  nil
```

You should find a significant performance impact from using the AOT'ed classes, such as from 6.8s to 2.2s - this is the cost of reading and compiling all of the code needed for dev time startup, which you can now pay once, rather than every time you start.


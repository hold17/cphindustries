# How to contribute

Contribution from outsiders is welcome, however please follow this guide on how to contribute to this repository.

## Getting Started

Fork, then clone the repository:

```
git clone git@github.com:<your-username>/cphindustries.git
```

Make your changes, and then push to your fork and [submit a pull request](https://github.com/hold17/cphindustries/compare).

## Commit Messages

* Limit the first line to a short descriptive message
* Use present tense ("Add punctuation" not "Added punctuation")
* Use the imperative mood ("Move container to ..." not "Moves container to ...")
* When only changing documentation, include `[ci skip]` in the commit message
* Avoid spelling mistakes

## Closing issues
When closing an issue, the commit message to the commit that closes the issue is very important. The commit name still follows the rules of [Commit Messages](#Closing issues). 
The important part is that it has to end with `(Resolves #<number>)`. As an example, a commit message that closes an issue of id 1, should be as following: 

> Fix button placement (Resolves #1)

## Style

In general all code should be written in english. That includes class names, variable names, comments etc. Additionally, please follow the following standards.

### Formatting

* Avoid inline comments (comments should preferably be above)
* Break long lines
* Delete trailing whitespace
* Use an empty line between methods
* Use empty lines around multi-line blocks
* Always include `{` and `}` in statements except when returning right away (`if (myString == null) return`)
* Use spaces around operators, except for unary operators, such as `!`

### Naming

* Avoid abbreviations.
* Avoid object types in names (`user_array`, `email_method`, `CalculatorClass`, `ReportModule`).
* Name variables created by a factory after the factory (`user_factory` creates `user`).
* Name variables, methods, and classes to reveal intent.
* Treat acronyms as words in names (`XmlHttpRequest` not `XMLHTTPRequest`), even if the acronym is the entire name (`class Html` not `class HTML`).

### Organizing Methods

* Order methods so that caller methods are earlier in the file than the methods they call.
* Order methods so that methods are as close as possible to other methods they call.
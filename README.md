# Ktlint plugin for Danger Kotlin

Plugin for [Danger Kotlin](https://google.com]) to allow you to import, parse and report any feedback from [Ktlint](https://github.com/pinterest/ktlint)

## How it looks

## Usage

Add the dependency to your `Dangerfile.df.kts`. You could check the last version available on [Maven Central]().

```
@file:DependsOn("io.github.bastosss77:ktlint-danger-kotlin:LATEST")

register.plugin(KtlintPlugin)
```

### How to

Parse a file and create a report

:warning: Currently, on Json format is supported 
```
val file = File("my/path/to/ktlint/report.json")
val report = KtlintPlugin.parse(file)

//You can do whatever you want with the report or just call the plugin to let Danger do its work

KtlintPlugin.report(report)
```

You can also use multiple files

```
val files = arrayOf(file1, file2)
val report = KtlintPlugin.parse(files)

KtlintPlugin.report(report)
```

#### Custom reporter

In case the default reporter doesn't match your needs, you can implement your own by implementing the `KtlintReporter` interface.

``` 
class MyReporter(val context: DangerContext) : KtlintReporter {
    override fun report(report: KtlintIssueReport) {
        //Do whatever you want
    }
}

KtlintPlugin.report(report, MyReporter(context))

```

## Todo

- [ ] Use a Regex to find Ktlint files
- [ ] Parse Sarif
- [ ] Parse XML

## Note
This plugin is inspired by [pavelkorolevxyz Detekt plugin](https://github.com/pavelkorolevxyz/danger-detekt-kotlin/tree/main?tab=readme-ov-file)
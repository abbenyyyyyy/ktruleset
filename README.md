[![](https://jitpack.io/v/abbenyyyyyy/ktruleset.svg)](https://jitpack.io/#abbenyyyyyy/ktruleset)
<h3 align="center">简介</h3>
自定义  [ktlint](https://github.com/pinterest/ktlint) 规则.  

1. [AndroidLogRule](https://github.com/abbenyyyyyy/ktruleset/blob/master/app/src/main/java/com/dsl/ktruleset/AndroidLogRule.kt) -- 不可使用 ```android.util.Log``` 方法.
2. [ExtendBaseRule](https://github.com/abbenyyyyyy/ktruleset/blob/master/app/src/main/java/com/dsl/ktruleset/ExtendBaseRule.kt) -- 不可直接继承 ```Activity()``` 、 ```Fragment()``` ```DialogFragment()``` 、 ```Dialog()``` , 必须继承 ```BaseActivity``` 、 ```BaseFragment``` 、 ```BaseDialogFragment```.
3. [KclassNoteRule](https://github.com/abbenyyyyyy/ktruleset/blob/master/app/src/main/java/com/dsl/ktruleset/KclassNoteRule.kt) -- 继承 ```BaseActivity``` 、 ```BaseFragment``` 的类必须要有注释.

<h3 align="center">使用方法</h3>

保证项目正常使用 [ktlint](https://github.com/pinterest/ktlint) ;

在项目根目录下的 build.gradle 文件添加 JitPack 仓库
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

在项目根目录下的 app 目录下的 build.gradle 文件引用该仓库 Jar

```gradle
  ...
  dependencies {
    ...
    ktlint "com.github.abbenyyyyyy:ktruleset:1.0.4"
    ...
  }
```

在根目录的 ```.editorconfig ``` 添加 ```ben_ktlint_emit_language=zh_CN``` 可以用简体中文输出警告，默认是英语
```
...
ben_ktlint_emit_language=zh_CN
```

<h3 align="center">构建</h3>

```git
git clone https://github.com/abbenyyyyyy/ktruleset.git
```

需保证系统环境变量 java1.8 能正常使用，使用 Android Studio 打开项目，然后在 Android Studio 里的 File --> Project Structrue 里正确配置 JDK location . 然后执行
```bash
./gradlew build
```
然后你就可以在项目根目录下的 app --> build --> libs 里看到构建的 app.jar .

<h3 align="center">License</h3>

The MIT License(http://opensource.org/licenses/MIT)

请自由地享受和参与开源
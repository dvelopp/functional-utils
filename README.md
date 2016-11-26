Java 8 provided such a lot great features connected to functional programming so that the way software is written was totally changed.
The amount of code to do the same things was significantly reduced and it became more understandable. 
However, personally, I believe that there is much more can be done to improve the way we write code even with java 8.
Thus, I decided to write utilities that helps to use many of java 8 features in more understandable and concise way. 

For now the project includes some utilities connected to:
- conditions;
- predicates and other functional interfaces;
- collections;
- some groovy-like features; 

I'm going to make as more as possible useful utilities that can help everyone during the development. 

For now there are about 100 unit tests that can demonstrate the functionality.

Examples:

- Quick mapping between collections:

```
mapToList(testObjects, TestObject::getSubTestObject);
mapToSet(testObjects, TestObject::getSubTestObject);
mapToCollection(testObjects, TestObject::getSubTestObject, HashSet::new);
```

- Negating predicate:
```
Predicate<Collection> isEmptyPredicate = Collection::isEmpty;
Predicate<Collection> isNotEmptyPredicate = not(isEmptyPredicate);
assertThat(isEmptyPredicate.test(notEmptyList)).isFalse();
assertThat(isNotEmptyPredicate.test(notEmptyList)).isTrue();
```
- Conciese concise:
```
Integer expectedValInTrueDefinition = 1;
Integer expectedValInFalseDefinition = 2;

Integer actualVal = inCase(identityPredicate(), true)
  .isTrue(expectedValInTrueDefinition)
  .isFalse(expectedValInFalseDefinition)
  .value();

assertThat(actualVal).isEqualTo(expectedValInTrueDefinition);
```

... and many other features are already here. 

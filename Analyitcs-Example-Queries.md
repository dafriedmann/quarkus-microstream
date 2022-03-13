## Exmaple queries for neo4j analytics


```
MATCH (person)--(city) RETURN person
```

```
MATCH (city {name:"München"})--(person) RETURN count(person) as count
```

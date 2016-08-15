CC=gcc
OBJ = test_spawn.o

%.o: %.c
	$(CC) -c -o $@ $<

test_spawn: $(OBJ)
	$(CC) -o $@ $^

.PHONY: clean

clean:
	rm -f *.o *~ core

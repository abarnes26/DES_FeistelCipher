# Overview

Studying Cryptography 1 Course hosted on Coursera
https://www.coursera.org/learn/crypto/home/info

Creating Ciphers in Java to better understand the fundamentals

## What
DES acts on 64 bit blocks
Input 64 bits, output 64 bits

## How
- Initial Permutation of input
- Iterate through plain text 16 times
- Split the input in half (L, R) each time and follow encryption / decryption formulas
- Final Permutation of output (inverse of the first)

### Key Expantion
- Expand 56 bit key into 16 unique 48 bit keys (i.e. "round keys")
- Each key is 48 bits and is unique to it's position.  
I.e. when encrypting you use 1 through 16 and when decrypting you start at 16 and move to 1


### Encryption 
R(0) becomes L(1)
L(0) gets XORed with the function of R(0) and becomes R(1)

L(i) = R(i) - 1
R(i) = (L(i)) XOR f(i)((R(i) -1))

### Decryption 
R(i) = L(i) + 1
L(i) = R(i) + 1 XOR f(i + 1)(R(i))
---- OR ----
R(0) = L(1)
L(0) = f(1)(L(1) XOR R(1)

### The "Function" (f)
Takes parameters of 32 bits of input and a 48 bit round key
Maps 32 bits to 32 bits
1. map the 32 bit input to 48 bits
2. XOR the 48 bit input with the 48 bit round key
3. Break 48 bits into 8 groups of 6 bits
4. Map the 6 bits to 4 bits using S-Boxes (x 8)
5. Concatenate the 6 collections into a 32 bit string
6. Feed the 32 bit string into another permutation (maps the bits around)
7. Output the 32 bits




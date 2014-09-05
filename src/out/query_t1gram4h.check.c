#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>
#ifndef MAP_FILE
#define MAP_FILE MAP_SHARED
#endif
int fsize(int fd) {
  struct stat stat;
  int res = fstat(fd,&stat);
  return stat.st_size;
}
int printll(char* s) {
  while (*s != '\n' && *s != ',' && *s != '\t') {
    putchar(*s++);
  }
  return 0;
}
unsigned long hash(unsigned char *str) // FIXME: need to take length!
{
  unsigned long hash = 5381;
  int c;
  while ((c = *str++))
  hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
  return hash;
}
void Snippet(char*);
int main(int argc, char *argv[])
{
  if (argc != 2) {
    printf("usage: query <filename>\n");
    return 0;
  }
  Snippet(argv[1]);
  return 0;
}
/*****************************************
Emitting C Generated Code
*******************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
void Snippet(char*  x0) {
  printf("%s\n","Phrase,Year,MatchCount,VolumeCount,Phrase");
  char** x2 = (char**)malloc(256 * sizeof(char*));
  int32_t* x3 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x4 = 0;
  int32_t x5 = 0;
  int32_t* x6 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x8=0; x8 < 256; x8++) {
    x6[x8] = -1;
  }
  char** x12 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x13 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x14 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x15 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x16 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x17 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x18 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x19 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t x20 = 0;
  int32_t x21 = 0;
  int32_t* x22 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t* x23 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x27 = 0;
  int32_t x24 = open(x0,0);
  int32_t x25 = fsize(x24);
  char* x26 = mmap(0, x25, PROT_READ, MAP_FILE | MAP_SHARED, x24, 0);
  for (;;) {
    int32_t x28 = x27;
    bool x29 = x28 < x25;
    if (!x29) break;
    int32_t x31 = x27;
    for (;;) {
      int32_t x32 = x27;
      char x33 = x26[x32];
      bool x34 = x33 != '\t';
      if (!x34) break;
      x27 += 1;
    }
    int32_t x39 = x27;
    x27 += 1;
    int32_t x43 = x27;
    for (;;) {
      int32_t x44 = x27;
      char x45 = x26[x44];
      bool x46 = x45 != '\t';
      if (!x46) break;
      x27 += 1;
    }
    int32_t x51 = x27;
    x27 += 1;
    int32_t x55 = x27;
    for (;;) {
      int32_t x56 = x27;
      char x57 = x26[x56];
      bool x58 = x57 != '\t';
      if (!x58) break;
      x27 += 1;
    }
    int32_t x63 = x27;
    x27 += 1;
    int32_t x67 = x27;
    for (;;) {
      int32_t x68 = x27;
      char x69 = x26[x68];
      bool x70 = x69 != '\n';
      if (!x70) break;
      x27 += 1;
    }
    int32_t x75 = x27;
    x27 += 1;
    int32_t x79 = x21;
    char* x42 = x26+x31;
    x12[x79] = x42;
    int32_t x40 = x39 - x31;
    x13[x79] = x40;
    char* x54 = x26+x43;
    x14[x79] = x54;
    int32_t x52 = x51 - x43;
    x15[x79] = x52;
    char* x66 = x26+x55;
    x16[x79] = x66;
    int32_t x64 = x63 - x55;
    x17[x79] = x64;
    char* x78 = x26+x67;
    x18[x79] = x78;
    int32_t x76 = x75 - x67;
    x19[x79] = x76;
    x21 += 1;
    char x89 = x42[0];
    int32_t x90 = (int32_t)x89;
    int32_t x91 = x90 & 255;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x92 = x91;
    for (;;) {
      int32_t x93 = x92;
      int32_t x94 = x6[x93];
      bool x96 = x94 == -1;
      bool x118;
      if (x96) {
        x118 = false;
      } else {
        char* x97 = x2[x94];
        int32_t x98 = x3[x94];
        bool x100 = x98 == x40;
        bool x115;
        if (x100) {
          int32_t x101 = 0;
          for (;;) {
            int32_t x102 = x101;
            bool x103 = x102 < x98;
            char x104 = x97[x102];
            char x105 = x42[x102];
            bool x106 = x104 == x105;
            bool x107 = x103 && x106;
            if (!x107) break;
            x101 += 1;
          }
          int32_t x112 = x101;
          bool x113 = x112 == x98;
          x115 = x113;
        } else {
          x115 = false;
        }
        bool x116 = !x115;
        x118 = x116;
      }
      if (!x118) break;
      int32_t x120 = x92;
      int32_t x121 = x120 + 1;
      int32_t x122 = x121 & 255;
      x92 = x122;
    }
    int32_t x126 = x92;
    int32_t x127 = x6[x126];
    bool x128 = x127 == -1;
    int32_t x136;
    if (x128) {
      int32_t x129 = x5;
      x2[x129] = x42;
      x3[x129] = x40;
      x5 += 1;
      x6[x126] = x129;
      x23[x129] = 0;
      x136 = x129;
    } else {
      x136 = x127;
    }
    int32_t x138 = x136;
    //#hash_lookup
    int32_t x139 = x23[x138];
    int32_t x140 = x138 * 256;
    int32_t x141 = x140 + x139;
    x22[x141] = x79;
    int32_t x143 = x139 + 1;
    x23[x138] = x143;
  }
  int32_t x151 = 0;
  int32_t x148 = open("src/data/words.csv",0);
  int32_t x149 = fsize(x148);
  char* x150 = mmap(0, x149, PROT_READ, MAP_FILE | MAP_SHARED, x148, 0);
  for (;;) {
    int32_t x152 = x151;
    char x153 = x150[x152];
    bool x154 = x153 != ',';
    if (!x154) break;
    x151 += 1;
  }
  int32_t x159 = x151;
  x151 += 1;
  int32_t x162 = x151;
  int32_t x163 = 0;
  for (;;) {
    int32_t x164 = x151;
    char x165 = x150[x164];
    bool x166 = x165 != '\n';
    if (!x166) break;
    int32_t x168 = x163;
    int32_t x170 = x151;
    int32_t x169 = x168 * 10;
    char x171 = x150[x170];
    char x172 = x171 - '0';
    int32_t x173 = x169 + x172;
    x163 = x173;
    x151 += 1;
  }
  x151 += 1;
  int32_t x179 = x163;
  for (;;) {
    int32_t x180 = x151;
    bool x181 = x180 < x149;
    if (!x181) break;
    int32_t x183 = x151;
    for (;;) {
      int32_t x184 = x151;
      char x185 = x150[x184];
      bool x186 = x185 != ',';
      if (!x186) break;
      x151 += 1;
    }
    int32_t x191 = x151;
    x151 += 1;
    int32_t x195 = x151;
    int32_t x196 = 0;
    for (;;) {
      int32_t x197 = x151;
      char x198 = x150[x197];
      bool x199 = x198 != '\n';
      if (!x199) break;
      int32_t x201 = x196;
      int32_t x203 = x151;
      int32_t x202 = x201 * 10;
      char x204 = x150[x203];
      char x205 = x204 - '0';
      int32_t x206 = x202 + x205;
      x196 = x206;
      x151 += 1;
    }
    x151 += 1;
    int32_t x212 = x196;
    char* x194 = x150+x183;
    char x213 = x194[0];
    int32_t x214 = (int32_t)x213;
    int32_t x215 = x214 & 255;
    int32_t x192 = x191 - x183;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x216 = x215;
    for (;;) {
      int32_t x217 = x216;
      int32_t x218 = x6[x217];
      bool x220 = x218 == -1;
      bool x242;
      if (x220) {
        x242 = false;
      } else {
        char* x221 = x2[x218];
        int32_t x222 = x3[x218];
        bool x224 = x222 == x192;
        bool x239;
        if (x224) {
          int32_t x225 = 0;
          for (;;) {
            int32_t x226 = x225;
            bool x227 = x226 < x222;
            char x228 = x221[x226];
            char x229 = x194[x226];
            bool x230 = x228 == x229;
            bool x231 = x227 && x230;
            if (!x231) break;
            x225 += 1;
          }
          int32_t x236 = x225;
          bool x237 = x236 == x222;
          x239 = x237;
        } else {
          x239 = false;
        }
        bool x240 = !x239;
        x242 = x240;
      }
      if (!x242) break;
      int32_t x244 = x216;
      int32_t x245 = x244 + 1;
      int32_t x246 = x245 & 255;
      x216 = x246;
    }
    int32_t x250 = x216;
    int32_t x251 = x6[x250];
    int32_t x253 = x251;
    //#hash_lookup
    int32_t x254 = x23[x253];
    int32_t x255 = x253 * 256;
    int32_t x256 = x255 + x254;
    for(int x258=x255; x258 < x256; x258++) {
      int32_t x259 = x22[x258];
      char* x260 = x12[x259];
      int32_t x261 = x13[x259];
      char* x262 = x14[x259];
      int32_t x263 = x15[x259];
      char* x264 = x16[x259];
      int32_t x265 = x17[x259];
      char* x266 = x18[x259];
      int32_t x267 = x19[x259];
      int32_t x268 = printll(x260);
      printf(",");
      int32_t x270 = printll(x262);
      printf(",");
      int32_t x272 = printll(x264);
      printf(",");
      int32_t x274 = printll(x266);
      printf(",");
      int32_t x276 = printll(x194);
      printf("%s\n","");
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/

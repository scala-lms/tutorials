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
  printf("%s\n","Name,Value,Flag,Name");
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
  int32_t* x14 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x15 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x16 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t x17 = 0;
  int32_t x18 = 0;
  int32_t* x19 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t* x20 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x24 = 0;
  int32_t x21 = open("src/data/t.csv",0);
  int32_t x22 = fsize(x21);
  char* x23 = mmap(0, x22, PROT_READ, MAP_FILE | MAP_SHARED, x21, 0);
  for (;;) {
    int32_t x25 = x24;
    char x26 = x23[x25];
    bool x27 = x26 != ',';
    if (!x27) break;
    x24 += 1;
  }
  int32_t x32 = x24;
  x24 += 1;
  int32_t x35 = x24;
  int32_t x36 = 0;
  for (;;) {
    int32_t x37 = x24;
    char x38 = x23[x37];
    bool x39 = x38 != ',';
    if (!x39) break;
    int32_t x41 = x36;
    int32_t x43 = x24;
    int32_t x42 = x41 * 10;
    char x44 = x23[x43];
    char x45 = x44 - '0';
    int32_t x46 = x42 + x45;
    x36 = x46;
    x24 += 1;
  }
  x24 += 1;
  int32_t x52 = x36;
  int32_t x53 = x24;
  for (;;) {
    int32_t x54 = x24;
    char x55 = x23[x54];
    bool x56 = x55 != '\n';
    if (!x56) break;
    x24 += 1;
  }
  int32_t x61 = x24;
  x24 += 1;
  for (;;) {
    int32_t x65 = x24;
    bool x66 = x65 < x22;
    if (!x66) break;
    int32_t x68 = x24;
    for (;;) {
      int32_t x69 = x24;
      char x70 = x23[x69];
      bool x71 = x70 != ',';
      if (!x71) break;
      x24 += 1;
    }
    int32_t x76 = x24;
    x24 += 1;
    int32_t x80 = x24;
    int32_t x81 = 0;
    for (;;) {
      int32_t x82 = x24;
      char x83 = x23[x82];
      bool x84 = x83 != ',';
      if (!x84) break;
      int32_t x86 = x81;
      int32_t x88 = x24;
      int32_t x87 = x86 * 10;
      char x89 = x23[x88];
      char x90 = x89 - '0';
      int32_t x91 = x87 + x90;
      x81 = x91;
      x24 += 1;
    }
    x24 += 1;
    int32_t x97 = x81;
    int32_t x98 = x24;
    for (;;) {
      int32_t x99 = x24;
      char x100 = x23[x99];
      bool x101 = x100 != '\n';
      if (!x101) break;
      x24 += 1;
    }
    int32_t x106 = x24;
    x24 += 1;
    int32_t x110 = x18;
    char* x79 = x23+x68;
    x12[x110] = x79;
    int32_t x77 = x76 - x68;
    x13[x110] = x77;
    x14[x110] = x97;
    char* x109 = x23+x98;
    x15[x110] = x109;
    int32_t x107 = x106 - x98;
    x16[x110] = x107;
    x18 += 1;
    char x117 = x79[0];
    int32_t x118 = (int32_t)x117;
    int32_t x119 = x118 & 255;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x120 = x119;
    for (;;) {
      int32_t x121 = x120;
      int32_t x122 = x6[x121];
      bool x124 = x122 == -1;
      bool x146;
      if (x124) {
        x146 = false;
      } else {
        char* x125 = x2[x122];
        int32_t x126 = x3[x122];
        bool x128 = x126 == x77;
        bool x143;
        if (x128) {
          int32_t x129 = 0;
          for (;;) {
            int32_t x130 = x129;
            bool x131 = x130 < x126;
            char x132 = x125[x130];
            char x133 = x79[x130];
            bool x134 = x132 == x133;
            bool x135 = x131 && x134;
            if (!x135) break;
            x129 += 1;
          }
          int32_t x140 = x129;
          bool x141 = x140 == x126;
          x143 = x141;
        } else {
          x143 = false;
        }
        bool x144 = !x143;
        x146 = x144;
      }
      if (!x146) break;
      int32_t x148 = x120;
      int32_t x149 = x148 + 1;
      int32_t x150 = x149 & 255;
      x120 = x150;
    }
    int32_t x154 = x120;
    int32_t x155 = x6[x154];
    bool x156 = x155 == -1;
    int32_t x164;
    if (x156) {
      int32_t x157 = x5;
      x2[x157] = x79;
      x3[x157] = x77;
      x5 += 1;
      x6[x154] = x157;
      x20[x157] = 0;
      x164 = x157;
    } else {
      x164 = x155;
    }
    int32_t x166 = x164;
    //#hash_lookup
    int32_t x167 = x20[x166];
    int32_t x168 = x166 * 256;
    int32_t x169 = x168 + x167;
    x19[x169] = x110;
    int32_t x171 = x167 + 1;
    x20[x166] = x171;
  }
  int32_t x175 = 0;
  for (;;) {
    int32_t x176 = x175;
    char x177 = x23[x176];
    bool x178 = x177 != ',';
    if (!x178) break;
    x175 += 1;
  }
  int32_t x183 = x175;
  x175 += 1;
  int32_t x185 = x175;
  int32_t x186 = 0;
  for (;;) {
    int32_t x187 = x175;
    char x188 = x23[x187];
    bool x189 = x188 != ',';
    if (!x189) break;
    int32_t x191 = x186;
    int32_t x193 = x175;
    int32_t x192 = x191 * 10;
    char x194 = x23[x193];
    char x195 = x194 - '0';
    int32_t x196 = x192 + x195;
    x186 = x196;
    x175 += 1;
  }
  x175 += 1;
  int32_t x202 = x186;
  int32_t x203 = x175;
  for (;;) {
    int32_t x204 = x175;
    char x205 = x23[x204];
    bool x206 = x205 != '\n';
    if (!x206) break;
    x175 += 1;
  }
  int32_t x211 = x175;
  x175 += 1;
  for (;;) {
    int32_t x215 = x175;
    bool x216 = x215 < x22;
    if (!x216) break;
    int32_t x218 = x175;
    for (;;) {
      int32_t x219 = x175;
      char x220 = x23[x219];
      bool x221 = x220 != ',';
      if (!x221) break;
      x175 += 1;
    }
    int32_t x226 = x175;
    x175 += 1;
    int32_t x230 = x175;
    int32_t x231 = 0;
    for (;;) {
      int32_t x232 = x175;
      char x233 = x23[x232];
      bool x234 = x233 != ',';
      if (!x234) break;
      int32_t x236 = x231;
      int32_t x238 = x175;
      int32_t x237 = x236 * 10;
      char x239 = x23[x238];
      char x240 = x239 - '0';
      int32_t x241 = x237 + x240;
      x231 = x241;
      x175 += 1;
    }
    x175 += 1;
    int32_t x247 = x231;
    int32_t x248 = x175;
    for (;;) {
      int32_t x249 = x175;
      char x250 = x23[x249];
      bool x251 = x250 != '\n';
      if (!x251) break;
      x175 += 1;
    }
    int32_t x256 = x175;
    x175 += 1;
    char* x229 = x23+x218;
    char x260 = x229[0];
    int32_t x261 = (int32_t)x260;
    int32_t x262 = x261 & 255;
    int32_t x227 = x226 - x218;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x263 = x262;
    for (;;) {
      int32_t x264 = x263;
      int32_t x265 = x6[x264];
      bool x267 = x265 == -1;
      bool x289;
      if (x267) {
        x289 = false;
      } else {
        char* x268 = x2[x265];
        int32_t x269 = x3[x265];
        bool x271 = x269 == x227;
        bool x286;
        if (x271) {
          int32_t x272 = 0;
          for (;;) {
            int32_t x273 = x272;
            bool x274 = x273 < x269;
            char x275 = x268[x273];
            char x276 = x229[x273];
            bool x277 = x275 == x276;
            bool x278 = x274 && x277;
            if (!x278) break;
            x272 += 1;
          }
          int32_t x283 = x272;
          bool x284 = x283 == x269;
          x286 = x284;
        } else {
          x286 = false;
        }
        bool x287 = !x286;
        x289 = x287;
      }
      if (!x289) break;
      int32_t x291 = x263;
      int32_t x292 = x291 + 1;
      int32_t x293 = x292 & 255;
      x263 = x293;
    }
    int32_t x297 = x263;
    int32_t x298 = x6[x297];
    int32_t x300 = x298;
    //#hash_lookup
    int32_t x301 = x20[x300];
    int32_t x302 = x300 * 256;
    int32_t x303 = x302 + x301;
    for(int x305=x302; x305 < x303; x305++) {
      int32_t x306 = x19[x305];
      char* x307 = x12[x306];
      int32_t x308 = x13[x306];
      int32_t x309 = x14[x306];
      char* x310 = x15[x306];
      int32_t x311 = x16[x306];
      int32_t x312 = printll(x307);
      printf(",");
      printf("%d",x309);
      printf(",");
      int32_t x316 = printll(x310);
      printf(",");
      int32_t x318 = printll(x229);
      printf("%s\n","");
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/

/*****************************************
Emitting C Generated Code
*******************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
void Snippet(char*  x0) {
  printf("%s\n","Name,Value,Flag,Name");
  char** x2 = (char**)malloc(65536 * sizeof(char*));
  char** x3 = (char**)malloc(65536 * sizeof(char*));
  char** x4 = (char**)malloc(65536 * sizeof(char*));
  int32_t x5 = 0;
  int32_t x6 = 0;
  int32_t* x7 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x8 = (char**)malloc(256 * sizeof(char*));
  int32_t x9 = 0;
  int32_t* x10 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x12=0; x12 < 256; x12++) {
    x10[x12] = 0;
  }
  void*/*TODO:Scanner*/ *x16 = NULL/*TODO:ScannerNew*/;
  char* x17 = NULL/*TODO:ScannerNext*/;
  char* x18 = NULL/*TODO:ScannerNext*/;
  char* x19 = NULL/*TODO:ScannerNext*/;
  for (;;) {
    bool x20 = NULL/*TODO:ScannerHasNext*/;
    if (!x20) break;
    char* x22 = NULL/*TODO:ScannerNext*/;
    char* x23 = NULL/*TODO:ScannerNext*/;
    char* x24 = NULL/*TODO:ScannerNext*/;
    int32_t x25 = x6;
    x2[x25] = x22;
    x3[x25] = x23;
    x4[x25] = x24;
    x6 += 1;
    //#hash_lookup
    // generated code for hash lookup
    int64_t x30 = x22.##;
    int32_t x31 = (int32_t)x30;
    int32_t x32 = x31 & 255;
    int32_t x33 = x32;
    for (;;) {
      int32_t x34 = x33;
      int32_t x35 = x10[x34];
      char* x37 = x8[x34];
      bool x36 = x35 != 0;
      bool x38 = x37 == x22;
      bool x39 = true && x38;
      bool x40 = !x39;
      bool x41 = x36 && x40;
      if (!x41) break;
      int32_t x43 = x33;
      int32_t x44 = x43 + 1;
      int32_t x45 = x44 & 255;
      x33 = x45;
    }
    int32_t x49 = x33;
    x8[x49] = x22;
    int32_t x52 = x49;
    //#hash_lookup
    int32_t x53 = x10[x52];
    int32_t x54 = x52 * 256;
    int32_t x55 = x54 + x53;
    x7[x55] = x25;
    int32_t x57 = x53 + 1;
    x10[x52] = x57;
  }
  void*/*TODO:Scanner*/ *x61 = NULL/*TODO:ScannerNew*/;
  char* x62 = NULL/*TODO:ScannerNext*/;
  char* x63 = NULL/*TODO:ScannerNext*/;
  char* x64 = NULL/*TODO:ScannerNext*/;
  for (;;) {
    bool x65 = NULL/*TODO:ScannerHasNext*/;
    if (!x65) break;
    char* x67 = NULL/*TODO:ScannerNext*/;
    char* x68 = NULL/*TODO:ScannerNext*/;
    char* x69 = NULL/*TODO:ScannerNext*/;
    //#hash_lookup
    // generated code for hash lookup
    int64_t x70 = x67.##;
    int32_t x71 = (int32_t)x70;
    int32_t x72 = x71 & 255;
    int32_t x73 = x72;
    for (;;) {
      int32_t x74 = x73;
      int32_t x75 = x10[x74];
      char* x77 = x8[x74];
      bool x76 = x75 != 0;
      bool x78 = x77 == x67;
      bool x79 = true && x78;
      bool x80 = !x79;
      bool x81 = x76 && x80;
      if (!x81) break;
      int32_t x83 = x73;
      int32_t x84 = x83 + 1;
      int32_t x85 = x84 & 255;
      x73 = x85;
    }
    int32_t x89 = x73;
    int32_t x91 = x89;
    //#hash_lookup
    int32_t x92 = x10[x91];
    int32_t x93 = x91 * 256;
    int32_t x94 = x93 + x92;
    for(int x96=x93; x96 < x94; x96++) {
      int32_t x97 = x7[x96];
      char* x98 = x2[x97];
      char* x99 = x3[x97];
      char* x100 = x4[x97];
      printf("\"%s,%s,%s,%s\n\"",x98,x99,x100,x67);
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/

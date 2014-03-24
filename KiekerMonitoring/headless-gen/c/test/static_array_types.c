/***************************************************************************
 * Copyright 2013 Kieker Project (http://kieker-monitoring.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
#include <stdlib.h>
#include <kieker.h>
#include "c/test/test_static_array_types.h"

/**
 * Author: Generic Kieker
 * Version: 1.10
 */
/*
 * Serialize an StaticArrayTypes and return the size of the written structure.
 *
 * buffer = the buffer to send the data
 * id = id to identify the record type
 * offset = store data to buffer at offset
 * value = the value to be stored
 *
 * returns size of written structure
 */
int test_static_array_types_serialize(char *buffer, const int id, const int offset, const test_static_array_types value) {
	int length = 0;
	length += kieker_serialize_boolean(buffer,offset,a);
	length += kieker_serialize_int8(buffer,offset,b);
	length += kieker_serialize_int16(buffer,offset,c);
	length += kieker_serialize_double(buffer,offset,d);
	length += kieker_serialize_float(buffer,offset,f);
	length += kieker_serialize_int32(buffer,offset,g);
	length += kieker_serialize_int64(buffer,offset,h);
	length += kieker_serialize_int16(buffer,offset,i);
	length += kieker_serialize_string(buffer,offset,s);
	return length;
}
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
#include "c/org/spp/kieker/geolocation/org_spp_kieker_geolocation_static_array.h"

/**
 * Author: Generic Kieker
 * Version: 1.10
 */
/*
 * Serialize an StaticArray and return the size of the written structure.
 *
 * buffer = the buffer to send the data
 * id = id to identify the record type
 * offset = store data to buffer at offset
 * value = the value to be stored
 *
 * returns size of written structure
 */
int org_spp_kieker_geolocation_static_array_serialize(char *buffer, const int id, const int offset, const org_spp_kieker_geolocation_static_array value) {
	int length = 0;
	length += kieker_serialize_int32(buffer,offset,v);
	length += kieker_serialize_int32(buffer,offset,combi);
	return length;
}
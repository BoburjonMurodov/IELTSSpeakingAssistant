cmake_minimum_required(VERSION 3.18.1)

project("native-lib")

add_library(
        native-lib

        SHARED

        # Includes source files.
        native-lib.cpp )

find_library( # Defines the log-lib variable.
        log-lib
        log )

target_link_libraries( # Links the log library.
        native-lib
        ${log-lib} )
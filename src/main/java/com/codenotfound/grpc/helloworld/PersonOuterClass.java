// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: person.proto

package com.codenotfound.grpc.helloworld;

public final class PersonOuterClass {
  private PersonOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_codenotfound_grpc_helloworld_Person_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_codenotfound_grpc_helloworld_Person_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_codenotfound_grpc_helloworld_Greeting_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_codenotfound_grpc_helloworld_Greeting_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014person.proto\022 com.codenotfound.grpc.he" +
      "lloworld\"/\n\006Person\022\022\n\nfirst_name\030\001 \001(\t\022\021" +
      "\n\tlast_name\030\002 \001(\t\"\033\n\010Greeting\022\017\n\007message" +
      "\030\001 \001(\t2u\n\021HelloWorldService\022`\n\010sayHello\022" +
      "(.com.codenotfound.grpc.helloworld.Perso" +
      "n\032*.com.codenotfound.grpc.helloworld.Gre" +
      "etingB\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_codenotfound_grpc_helloworld_Person_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_codenotfound_grpc_helloworld_Person_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_codenotfound_grpc_helloworld_Person_descriptor,
        new java.lang.String[] { "FirstName", "LastName", });
    internal_static_com_codenotfound_grpc_helloworld_Greeting_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_codenotfound_grpc_helloworld_Greeting_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_codenotfound_grpc_helloworld_Greeting_descriptor,
        new java.lang.String[] { "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

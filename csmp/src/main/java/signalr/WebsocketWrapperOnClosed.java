// Copyright (c) .NET Foundation. All rights reserved.
// Licensed under the Apache License, Version 2.0. See License.txt in the project root for license information.

package signalr;

interface WebSocketOnClosedCallback {
    void invoke(Integer code, String reason);
}
